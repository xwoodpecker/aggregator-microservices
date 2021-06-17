package de.htw.saar.smartcity.aggregator.lib.broker;

import com.rabbitmq.client.Channel;
import de.htw.saar.smartcity.aggregator.lib.base.Constants;
import de.htw.saar.smartcity.aggregator.lib.properties.ApplicationProperties;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public abstract class Publisher extends BrokerConnection {

    public Publisher(ApplicationProperties applicationProperties) {

        super(applicationProperties);

        try {

            channel.exchangeDeclare(Constants.GROUP_EXCHANGE, "topic");
        } catch (IOException e) {
            log.error("Error during publisher channel instantiation.");
            e.printStackTrace();
        }
    }

    public void publish (String queueName, String message)  {
        try {
            channel.basicPublish(Constants.GROUP_EXCHANGE, queueName, null, message.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            log.error("Error during publish on group exchange");
            e.printStackTrace();
        }
    }

}
