package de.htw.saar.smartcity.aggregator.receiver;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import de.htw.saar.smartcity.aggregator.handler.MeasurementHandler;
import de.htw.saar.smartcity.aggregator.model.SensorMeasurement;
import de.htw.saar.smartcity.aggregator.properties.MicroserviceApplicationProperties;
import java.io.IOException;


public abstract class Receiver extends ReceiverConnection{

    protected final MeasurementHandler measurementHandler;

    public Receiver(MicroserviceApplicationProperties applicationProperties, MeasurementHandler measurementHandler) {

        super(applicationProperties);
        this.measurementHandler = measurementHandler;

        Channel channel;
        try {
            channel = connection.createChannel();

            channel.queueDeclare(applicationProperties.getMicroserviceQueue(), true, false, false, null);

            for(String topic : applicationProperties.getMicroserviceTopics()) {
                String routingKey = topic.replaceAll("/", ".");
                channel.queueBind(applicationProperties.getMicroserviceQueue(), "amq.topic", routingKey);
            }

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                String routingKey = delivery.getEnvelope().getRoutingKey();
                String topic = routingKey.replaceAll("\\.", "/");
                measurementHandler.handleMessage(new SensorMeasurement(topic, message));
            };
            channel.basicConsume(applicationProperties.getMicroserviceQueue(), true, deliverCallback, consumerTag -> {});

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
