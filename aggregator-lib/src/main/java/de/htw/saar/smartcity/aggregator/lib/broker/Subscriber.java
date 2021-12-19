package de.htw.saar.smartcity.aggregator.lib.broker;

import de.htw.saar.smartcity.aggregator.lib.handler.RawMeasurementHandler;
import de.htw.saar.smartcity.aggregator.lib.model.SensorMeasurement;
import de.htw.saar.smartcity.aggregator.lib.properties.RawMicroserviceApplicationProperties;
import de.htw.saar.smartcity.aggregator.lib.utils.Utils;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * The type Subscriber.
 */
@Deprecated
public abstract class Subscriber implements MqttCallback {

    /**
     * mqtt client
     */
    private final MqttClient mqttClient;

    /**
     * The Application properties.
     */
    protected final RawMicroserviceApplicationProperties applicationProperties;

    /**
     * The Raw measurement handler.
     */
    protected final RawMeasurementHandler rawMeasurementHandler;


    /**
     * Instantiates a new Mqtt subscriber.
     *
     * @param applicationProperties the application properties
     * @param rawMeasurementHandler the raw measurement handler
     * @throws Exception the exception
     */
    public Subscriber(RawMicroserviceApplicationProperties applicationProperties, RawMeasurementHandler rawMeasurementHandler) throws Exception {
        this.applicationProperties = applicationProperties;
        this.rawMeasurementHandler = rawMeasurementHandler;

        this.mqttClient = configMqttClient(this);

        subscribe(applicationProperties.getMicroserviceTopics());
    }



    /**
     * connection lost event
     *
     * @param cause
     */
    @Override
    public void connectionLost(Throwable cause) {
    }

    /**
     * message arrived event
     *
     * @param topic the topic the message arrived on
     * @param message the message that it sent
     */

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        rawMeasurementHandler.handleMessage(new SensorMeasurement(topic, message.toString()));
    }


    /**
     * delivery complete event
     *
     * @param token
     */
    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }

    /**
     * Subscribe to the topic
     *
     * @param topics the topics
     */
    private void subscribe(String[] topics) throws MqttException {
        this.mqttClient.subscribe(topics);
    }

    /**
     * configure mqtt client, register callback of subscriber
     * @param callback the subscriber call back
     * @return configured mqtt client
     * @throws Exception in case of error thrown
     */
    private MqttClient configMqttClient(Subscriber callback) throws Exception {
        String brokerAddress = ""; //applicationProperties.getBrokerAddress()
        MqttClient mqttClient = null;
        MqttConnectOptions connectionOptions = new MqttConnectOptions();

        String clientId = MqttClient.generateClientId();
        mqttClient = new MqttClient(brokerAddress, clientId, new MemoryPersistence());
        connectionOptions.setCleanSession(true);

        if (!Utils.isBlankOrNull(applicationProperties.getBrokerUserName())) {
            connectionOptions.setUserName(applicationProperties.getBrokerUserName());
        }
        if (!Utils.isBlankOrNull(applicationProperties.getBrokerPassword())) {
            connectionOptions.setPassword(applicationProperties.getBrokerPassword().toCharArray());
        }
        if (!Utils.isBlankOrNull(applicationProperties.getCaFile()) && !Utils.isBlankOrNull(applicationProperties.getClientCertFile()) && !Utils.isBlankOrNull(applicationProperties.getClientKeyFile())) {
            connectionOptions.setSocketFactory(BrokerUtils.getSSLSocketFactory(applicationProperties.getCaFile(), applicationProperties.getClientCertFile(), applicationProperties.getClientKeyFile()));
        }

        if(callback != null)
            mqttClient.setCallback(callback);

        mqttClient.connect(connectionOptions);

        return mqttClient;
    }



}
