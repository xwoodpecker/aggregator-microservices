package de.htw.saar.smartcity.aggregator.lib.broker;

import de.htw.saar.smartcity.aggregator.lib.handler.RawMeasurementHandler;
import de.htw.saar.smartcity.aggregator.lib.model.SensorMeasurement;
import de.htw.saar.smartcity.aggregator.lib.properties.RawMicroserviceApplicationProperties;
import de.htw.saar.smartcity.aggregator.lib.utils.Utils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;

@Deprecated
public abstract class Subscriber implements MqttCallback {

    /**
     * mqtt client
     */
    private final MqttClient mqttClient;

    protected final RawMicroserviceApplicationProperties applicationProperties;

    protected final RawMeasurementHandler rawMeasurementHandler;



    /**
     * Instantiates a new Mqtt subscriber.
     * @param applicationProperties
     * @param rawMeasurementHandler
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
        if (!Utils.isBlankOrNull(applicationProperties.getCaFile()) && !Utils.isBlankOrNull(applicationProperties.getClientCertFile()) && !Utils.isBlankOrNull(applicationProperties.getClientCertFile())) {
            connectionOptions.setSocketFactory(BrokerUtils.getSSLSocketFactory(applicationProperties.getCaFile(), applicationProperties.getClientCertFile(), applicationProperties.getClientKeyFile()));
        }

        if(callback != null)
            mqttClient.setCallback(callback);

        mqttClient.connect(connectionOptions);

        return mqttClient;
    }



}
