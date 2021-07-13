package de.htw.saar.smartcity.aggregator.lib.broker;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import de.htw.saar.smartcity.aggregator.lib.properties.BrokerApplicationProperties;
import de.htw.saar.smartcity.aggregator.lib.properties.MicroserviceApplicationProperties;
import de.htw.saar.smartcity.aggregator.lib.utils.Utils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

public abstract class BrokerConnection {

    protected static final Logger log = LoggerFactory.getLogger(BrokerConnection.class);

    protected final BrokerApplicationProperties applicationProperties;

    protected final Connection connection;

    protected Channel channel;


    public BrokerConnection(BrokerApplicationProperties applicationProperties) throws Exception {
        this.applicationProperties = applicationProperties;

        connection = configConnection();
        channel = connection.createChannel();
    }

    private Connection configConnection() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();

        Connection connection = null;
        factory.setHost(applicationProperties.getBrokerHost());
        factory.setPort(Integer.valueOf(applicationProperties.getBrokerPortAMQP()));

        if (!Utils.isBlankOrNull(applicationProperties.getBrokerUserName())) {
            factory.setUsername(applicationProperties.getBrokerUserName());
        }
        if (!Utils.isBlankOrNull(applicationProperties.getBrokerPassword())) {
            factory.setPassword(applicationProperties.getBrokerPassword());
        }
        if (!Utils.isBlankOrNull(applicationProperties.getCaFile()) &&
                !Utils.isBlankOrNull(applicationProperties.getClientCertFile()) &&
                !Utils.isBlankOrNull(applicationProperties.getClientKeyFile())) {

            factory.useSslProtocol(BrokerUtils.getSSLContext(applicationProperties.getCaFile(),
                    applicationProperties.getClientCertFile(),
                    applicationProperties.getClientKeyFile()));
        }

        connection = factory.newConnection(Executors.newFixedThreadPool(1));

        return connection;
    }




}
