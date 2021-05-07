package de.htw.saar.smartcity.aggregator.receiver;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import de.htw.saar.smartcity.aggregator.properties.ApplicationProperties;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public abstract class ReceiverConnection {
    protected final ApplicationProperties applicationProperties;

    protected final Connection connection;


    public ReceiverConnection(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
        connection = configConnection();
    }

    private Connection configConnection() {
        ConnectionFactory factory = new ConnectionFactory();
        /**factory.setHost("dsl-cluster-master");
         factory.setPort(5672);
         factory.setUsername("user");
         factory.setPassword("DgBe3RZVvZ");
         factory.useSslProtocol(getSSLContext(caFile, clientCertFile, clientKeyFile)); **/

        Connection connection = null;
        try {
            factory.setHost(applicationProperties.getBrokerHost());
            factory.setPort(Integer.valueOf(applicationProperties.getBrokerPort()));
            connection = factory.newConnection();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }


    /**
     * Gets ssl context for given certificates
     *
     * @param caFile the ca file
     * @param clientCertFile the ca file
     * @param clientKeyFile the client key file
     * @return ssl context
     * @throws Exception the exception
     */
    protected static SSLContext getSSLContext(final String caFile, final String clientCertFile, final String clientKeyFile)
            throws Exception {


        Security.addProvider(new BouncyCastleProvider());

        // load CA certificate
        X509Certificate caCert = null;

        InputStream stream = new ByteArrayInputStream(Base64.getDecoder().decode(caFile));
        BufferedInputStream bis = new BufferedInputStream(stream);
        CertificateFactory cf = CertificateFactory.getInstance("X.509");

        while (bis.available() > 0) {
            caCert = (X509Certificate) cf.generateCertificate(bis);
        }

        // load client certificate
        stream = new ByteArrayInputStream(Base64.getDecoder().decode(clientCertFile));
        bis = new BufferedInputStream(stream);
        X509Certificate cert = null;
        while (bis.available() > 0) {
            cert = (X509Certificate) cf.generateCertificate(bis);
        }

        // load client private key
        byte[] keyArray = Base64.getDecoder().decode(clientKeyFile);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyArray);
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

        return context;
    }
}
