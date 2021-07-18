package de.htw.saar.smartcity.aggregator.lib.broker;

import com.rabbitmq.client.DeliverCallback;
import de.htw.saar.smartcity.aggregator.lib.base.Constants;
import de.htw.saar.smartcity.aggregator.lib.properties.MicroserviceApplicationProperties;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.io.IOException;

/**
 * The type Receiver.
 */
public abstract class Receiver extends BrokerConnection {

    private final MicroserviceApplicationProperties microserviceApplicationProperties;

    private final ActivityManager activityManager;

    /**
     * Instantiates a new Receiver.
     *
     * @param applicationProperties the application properties
     * @param activityManager       the activity manager
     * @throws Exception the exception
     */
    public Receiver(MicroserviceApplicationProperties applicationProperties, ActivityManager activityManager) throws Exception {

        super(applicationProperties);
        microserviceApplicationProperties = applicationProperties;
        this.activityManager = activityManager;

        // durable: true, exclusive: false, autoDelete: false
        channel.queueDeclare(applicationProperties.getMicroserviceQueue(), true, false, false, null);
        // set prefetch count for limiting the amount of messages taken from the queue
        channel.basicQos(Constants.PREFETCH_COUNT, false);

    }


    /**
     * registers the callback after the application is setup
     * guarantees the SetupDataLoader to run before
     *
     * @throws IOException
     */
    @EventListener(ApplicationReadyEvent.class)
    private void registerCallback() throws IOException {
        // will be invoked for every message taken from the queue
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            long startTime = System.currentTimeMillis();
            processMessage(delivery.getEnvelope().getRoutingKey(), new String(delivery.getBody(), "UTF-8"));
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), true);
            long stopTime = System.currentTimeMillis();
            this.activityManager.addTime(stopTime, stopTime - startTime);
        };

        // messages get manually acknowledged so, autoAck is set to false
        channel.basicConsume(microserviceApplicationProperties.getMicroserviceQueue(), false, deliverCallback, consumerTag -> {});
    }

    /**
     * Process message.
     *
     * @param routingKey the routing key
     * @param message    the message
     */
    abstract void processMessage(String routingKey, String message);
}
