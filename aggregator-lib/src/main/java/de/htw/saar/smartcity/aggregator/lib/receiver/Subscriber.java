package de.htw.saar.smartcity.aggregator.lib.receiver;

import de.htw.saar.smartcity.aggregator.lib.handler.MeasurementHandler;
import de.htw.saar.smartcity.aggregator.lib.model.SensorMeasurement;
import de.htw.saar.smartcity.aggregator.lib.properties.BaseMicroserviceApplicationProperties;
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

public abstract class Subscriber implements MqttCallback {

    /**
     * mqtt client
     */
    private final MqttClient mqttClient;

    protected final BaseMicroserviceApplicationProperties applicationProperties;

    protected final MeasurementHandler measurementHandler;



    /**
     * Instantiates a new Mqtt subscriber.
     * @param applicationProperties
     * @param measurementHandler
     */
    public Subscriber(BaseMicroserviceApplicationProperties applicationProperties, MeasurementHandler measurementHandler) {
        this.applicationProperties = applicationProperties;
        this.measurementHandler = measurementHandler;

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
        measurementHandler.handleMessage(new SensorMeasurement(topic, message.toString()));
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
    private void subscribe(String[] topics) {
        try {
            this.mqttClient.subscribe(topics);
        } catch (MqttException me) {
            me.printStackTrace();
        }
    }

    private MqttClient configMqttClient(Subscriber callback) {
        String brokerAddress = ""; //applicationProperties.getBrokerAddress()
        MqttClient mqttClient = null;
        MqttConnectOptions connectionOptions = new MqttConnectOptions();
        try {
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
                connectionOptions.setSocketFactory(getSocketFactoryForCertificates(applicationProperties.getCaFile(), applicationProperties.getClientCertFile(), applicationProperties.getClientKeyFile()));
            }

            if(callback != null)
                mqttClient.setCallback(callback);

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
