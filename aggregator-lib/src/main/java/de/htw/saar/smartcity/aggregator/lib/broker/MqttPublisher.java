package de.htw.saar.smartcity.aggregator.lib.broker;

import de.htw.saar.smartcity.aggregator.lib.properties.BrokerApplicationProperties;
import de.htw.saar.smartcity.aggregator.lib.utils.Utils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public abstract class MqttPublisher {

    private static final Logger log = LoggerFactory.getLogger(MqttPublisher.class);

    /**
     * mqtt client
     */
    private final MqttClient mqttClient;

    private final BrokerApplicationProperties applicationProperties;


    /**
     * Instantiates a new Mqtt subscriber.
     * @param applicationProperties
     */
    public MqttPublisher(BrokerApplicationProperties applicationProperties) throws Exception {
        this.applicationProperties = applicationProperties;

        this.mqttClient = configMqttClient();
        this.start();
    }


    /**
     * start the Publisher routine
     */
    protected abstract void start();


    /**
     * Publish message.
     *
     * @param topic       the topic
     * @param message     the message
     * @throws MqttException the mqtt exception
     */
    public void publish(String topic, String message) throws MqttException {

        final MqttTopic mqttTopic = mqttClient.getTopic(topic);
        mqttTopic.publish(new MqttMessage(message.getBytes()));

        log.info("Published data. Topic: " + mqttTopic.getName() + "  Message: " + Utils.limitLoggedMsg(message,150));
    }



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
