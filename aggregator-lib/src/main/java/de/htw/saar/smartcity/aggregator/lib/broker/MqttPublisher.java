package de.htw.saar.smartcity.aggregator.lib.broker;

import de.htw.saar.smartcity.aggregator.lib.base.Constants;
import de.htw.saar.smartcity.aggregator.lib.properties.BrokerApplicationProperties;
import de.htw.saar.smartcity.aggregator.lib.utils.Utils;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The type Mqtt publisher.
 */
public abstract class MqttPublisher implements Publisher{

    private static final Logger log = LoggerFactory.getLogger(MqttPublisher.class);

    /**
     * mqtt client
     */
    private final MqttClient mqttClient;

    /**
     * application settings for the broker
     */
    private final BrokerApplicationProperties applicationProperties;


    /**
     * Instantiates a new Mqtt publisher.
     *
     * @param applicationProperties the application properties
     * @throws Exception the exception
     */
    public MqttPublisher(BrokerApplicationProperties applicationProperties) throws Exception {
        this.applicationProperties = applicationProperties;

        this.mqttClient = configMqttClient();
    }


    /**
     * Publish message.
     *
     * @param topic   the topic
     * @param message the message
     * @throws MqttException the mqtt exception
     */
    public void publish(String topic, String message)  {

        final MqttTopic mqttTopic = mqttClient.getTopic(topic);
        try {
            mqttTopic.publish(new MqttMessage(message.getBytes()));
        } catch (MqttException e) {
            log.error("Exception during publish.");
        }

        log.info("Published data. Topic: " + mqttTopic.getName() + "  Message: " + Utils.limitLoggedMsg(message, Constants.MAX_LOG_MESSAGE_SIZE));
    }


    /**
     * Mqtt client configuration
     * @return the configured MqttClient
     * @throws Exception in case of error thrown
     */
    private MqttClient configMqttClient() throws Exception {
        boolean useSSL = !Utils.isBlankOrNull(applicationProperties.getCaFile()) && !Utils.isBlankOrNull(applicationProperties.getClientCertFile()) && !Utils.isBlankOrNull(applicationProperties.getClientKeyFile());
        String protocol = useSSL ? "ssl" : "tcp";
        String brokerAddress = String.format("%s://%s:%s", protocol,
                applicationProperties.getBrokerHost(),
                applicationProperties.getBrokerPortMQTT());
        MqttClient mqttClient = null;
        MqttConnectOptions connectionOptions = new MqttConnectOptions();

        String clientId = MqttClient.generateClientId();
        mqttClient = new MqttClient(brokerAddress, clientId, new MemoryPersistence());
        connectionOptions.setCleanSession(true);
        connectionOptions.setMaxInflight(100000); //maybe limit, high number for testing

        if (!Utils.isBlankOrNull(applicationProperties.getBrokerUserName())) {
            connectionOptions.setUserName(applicationProperties.getBrokerUserName());
        }
        if (!Utils.isBlankOrNull(applicationProperties.getBrokerPassword())) {
            connectionOptions.setPassword(applicationProperties.getBrokerPassword().toCharArray());
        }
        if (useSSL) {
            connectionOptions.setSocketFactory(
                    BrokerUtils.getSSLSocketFactory(applicationProperties.getCaFile(),
                            applicationProperties.getClientCertFile(),
                            applicationProperties.getClientKeyFile()
                    )
            );
        }

        mqttClient.connect(connectionOptions);

        return mqttClient;
    }
}
