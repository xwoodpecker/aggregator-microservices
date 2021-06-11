package de.htw.saar.smartcity.aggregator.lib.broker;

import de.htw.saar.smartcity.aggregator.lib.handler.RawMeasurementHandler;
import de.htw.saar.smartcity.aggregator.lib.model.SensorMeasurement;
import de.htw.saar.smartcity.aggregator.lib.properties.BrokerApplicationProperties;
import de.htw.saar.smartcity.aggregator.lib.properties.RawMicroserviceApplicationProperties;
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
import java.io.FileInputStream;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;

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
    public MqttPublisher(BrokerApplicationProperties applicationProperties) {
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
        log.info("Published data. Topic: " + mqttTopic.getName() + "  Message: " + message);
    }



    private MqttClient configMqttClient() {
        String brokerAddress = String.format("tcp://%s:%s", applicationProperties.getBrokerHost(), applicationProperties.getBrokerPort()); //todo: refactor
        MqttClient mqttClient = null;
        MqttConnectOptions connectionOptions = new MqttConnectOptions();
        try {
            String clientId = MqttClient.generateClientId();
            mqttClient = new MqttClient(brokerAddress, clientId, new MemoryPersistence());
            connectionOptions.setCleanSession(true);
            connectionOptions.setMaxInflight(1000);

            if (!Utils.isBlankOrNull(applicationProperties.getBrokerUserName())) {
                connectionOptions.setUserName(applicationProperties.getBrokerUserName());
            }
            if (!Utils.isBlankOrNull(applicationProperties.getBrokerPassword())) {
                connectionOptions.setPassword(applicationProperties.getBrokerPassword().toCharArray());
            }
            if (!Utils.isBlankOrNull(applicationProperties.getCaFile()) && !Utils.isBlankOrNull(applicationProperties.getClientCertFile()) && !Utils.isBlankOrNull(applicationProperties.getClientCertFile())) {
                connectionOptions.setSocketFactory(getSocketFactoryForCertificates(applicationProperties.getCaFile(), applicationProperties.getClientCertFile(), applicationProperties.getClientKeyFile()));
            }

            mqttClient.connect(connectionOptions);

        } catch (MqttException me) {
            me.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mqttClient;
    }


    /**
     * Gets socket factory for ca certificate.
     *
     * @param caFile the ca file
     * @param clientCertFile the ca file
     * @param clientKeyFile the client key file
     * @return the socket factory for ca certificate
     * @throws Exception the exception
     */
    private SSLSocketFactory getSocketFactoryForCertificates(final String caFile, final String clientCertFile, final String clientKeyFile)
            throws Exception {

        Security.addProvider(new BouncyCastleProvider());

        // load CA certificate
        X509Certificate caCert = null;

        FileInputStream fis = new FileInputStream(caFile);
        BufferedInputStream bis = new BufferedInputStream(fis);
        CertificateFactory cf = CertificateFactory.getInstance("X.509");

        while (bis.available() > 0) {
            caCert = (X509Certificate) cf.generateCertificate(bis);
        }

        // load client certificate
        bis = new BufferedInputStream(new FileInputStream(clientCertFile));
        X509Certificate cert = null;
        while (bis.available() > 0) {
            cert = (X509Certificate) cf.generateCertificate(bis);
        }

        // load client private key
        fis = new FileInputStream(clientKeyFile);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(fis.readAllBytes());
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PrivateKey privateKey =  kf.generatePrivate(spec);

        // CA certificate is used to authenticate server
        KeyStore caKs = KeyStore.getInstance(KeyStore.getDefaultType());
        caKs.load(null, null);
        caKs.setCertificateEntry("ca-certificate", caCert);
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(caKs);

        // client key and certificates are sent to server so it can authenticate us
        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
        ks.load(null, null);
        ks.setCertificateEntry("certificate", cert);
        ks.setKeyEntry("private-key", privateKey, null, new java.security.cert.Certificate[] { cert });
        KeyManagerFactory kmf = KeyManagerFactory.getInstance("PKIX");
        kmf.init(ks, null);

        // finally, create SSL socket factory
        SSLContext context = SSLContext.getInstance("TLSv1.2");
        context.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

        return context.getSocketFactory();
    }
}
