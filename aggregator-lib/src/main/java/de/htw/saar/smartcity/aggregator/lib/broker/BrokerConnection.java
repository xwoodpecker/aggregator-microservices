package de.htw.saar.smartcity.aggregator.lib.broker;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import de.htw.saar.smartcity.aggregator.lib.properties.BrokerApplicationProperties;
import de.htw.saar.smartcity.aggregator.lib.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;

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
