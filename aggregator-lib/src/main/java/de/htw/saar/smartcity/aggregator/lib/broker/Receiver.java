package de.htw.saar.smartcity.aggregator.lib.broker;

import com.rabbitmq.client.DeliverCallback;
import de.htw.saar.smartcity.aggregator.lib.base.Constants;
import de.htw.saar.smartcity.aggregator.lib.properties.MicroserviceApplicationProperties;

import java.io.IOException;

public abstract class Receiver extends BrokerConnection {

    private final ActivityManager activityManager;

    public Receiver(MicroserviceApplicationProperties applicationProperties, ActivityManager activityManager) {

        super(applicationProperties);
        this.activityManager = activityManager;

        try {
            // durable: true, exclusive: false, autoDelete: false
            channel.queueDeclare(applicationProperties.getMicroserviceQueue(), true, false, false, null);
            // set prefetch count for limiting the amount of messages taken from the queue
            channel.basicQos(Constants.PREFETCH_COUNT, false);

            // will be invoked for every message taken from the queue
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                long startTime = System.currentTimeMillis();
                processMessage(delivery.getEnvelope().getRoutingKey(), new String(delivery.getBody(), "UTF-8"));
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), true);
                long stopTime = System.currentTimeMillis();
                this.activityManager.addTime(stopTime, stopTime - startTime);
            };

            // messages get manually acknowledged so, autoAck is set to false
            channel.basicConsume(applicationProperties.getMicroserviceQueue(), false, deliverCallback, consumerTag -> {});
        }
        catch (IOException e) {
            log.error("Error during receiver channel instantiation.");
            e.printStackTrace();
        }
    }

    abstract void processMessage(String routingKey, String message);
}
