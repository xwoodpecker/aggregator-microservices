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
            channel.queueDeclare(applicationProperties.getMicroserviceQueue(), true, false, false, null);
            channel.basicQos(Constants.PREFETCH_COUNT, false);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                long startTime = System.currentTimeMillis();
                processMessage(delivery.getEnvelope().getRoutingKey(), new String(delivery.getBody(), "UTF-8"));
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), true);
                long stopTime = System.currentTimeMillis();
                this.activityManager.addTime(stopTime, stopTime - startTime);
            };

            channel.basicConsume(applicationProperties.getMicroserviceQueue(), false, deliverCallback, consumerTag -> {});
        }
        catch (IOException e) {
            log.error("Error during receiver channel instantiation.");
            e.printStackTrace();
        }
    }

    abstract void processMessage(String routingKey, String message);
}
