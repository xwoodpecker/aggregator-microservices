package de.htw.saar.smartcity.aggregator.lib.broker;

import de.htw.saar.smartcity.aggregator.lib.base.Constants;
import de.htw.saar.smartcity.aggregator.lib.properties.BrokerApplicationProperties;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * The type Publisher.
 */
public abstract class AMQPPublisher extends BrokerConnection implements Publisher {

    /**
     * Instantiates a new Publisher.
     *
     * @param applicationProperties the application properties
     * @throws Exception the exception
     */
    public AMQPPublisher(BrokerApplicationProperties applicationProperties) throws Exception {

        super(applicationProperties);

        channel.exchangeDeclare(Constants.GROUP_EXCHANGE, "topic");
    }

    /**
     * Publish.
     *
     * @param routingKey the routing key
     * @param message    the message
     */
    public void publish (String routingKey, String message)  {
        try {
            channel.basicPublish(Constants.GROUP_EXCHANGE, routingKey, null, message.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            log.error("Error during publish on group_exchange");
            //e.printStackTrace();
        }
    }

}
