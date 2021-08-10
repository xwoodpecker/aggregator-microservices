package de.htw.saar.smartcity.aggregator.lib.broker;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import de.htw.saar.smartcity.aggregator.lib.properties.BrokerApplicationProperties;
import de.htw.saar.smartcity.aggregator.lib.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;

/**
 * The type Broker connection.
 */
public abstract class BrokerConnection {

    /**
     * The constant log.
     */
    protected static final Logger log = LoggerFactory.getLogger(BrokerConnection.class);

    /**
     * The Application properties.
     */
    protected final BrokerApplicationProperties applicationProperties;

    /**
     * The Connection.
     */
    protected final Connection connection;

    /**
     * The Channel.
     */
    protected Channel channel;


    /**
     * Instantiates a new Broker connection.
     *
     * @param applicationProperties the application properties
     * @throws Exception the exception
     */
    public BrokerConnection(BrokerApplicationProperties applicationProperties) throws Exception {
        this.applicationProperties = applicationProperties;

        connection = configConnection();
        channel = connection.createChannel();
    }

    /**
     * configures the connection, authentication, sslcontext
     *
     * @return Connection the resulting connection
     * @throws Exception in case of failure throws exception
     */
    private Connection configConnection() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();

        Connection connection;
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

        // only use one thread for our connection!!
        connection = factory.newConnection(Executors.newFixedThreadPool(1));

        return connection;
    }




}
