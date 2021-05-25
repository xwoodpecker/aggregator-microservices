package de.htw.saar.smartcity.aggregator.lib.broker;

import com.rabbitmq.client.Channel;
import de.htw.saar.smartcity.aggregator.lib.properties.ApplicationProperties;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public abstract class Publisher extends BrokerConnection {

    private static final String EXCHANGE_NAME = "group_exchange";

    private Channel channel;

    public Publisher(ApplicationProperties applicationProperties) {

        super(applicationProperties);

        try {
            channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, "topic");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void publish (String queueName, String message)  {
        try {
            channel.basicPublish(EXCHANGE_NAME, queueName, null, message.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            //todo : log.error
            e.printStackTrace();
        }
    }

}
