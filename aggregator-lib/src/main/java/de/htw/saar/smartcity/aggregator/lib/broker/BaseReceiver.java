package de.htw.saar.smartcity.aggregator.lib.broker;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import de.htw.saar.smartcity.aggregator.lib.handler.RawMeasurementHandler;
import de.htw.saar.smartcity.aggregator.lib.model.SensorMeasurement;
import de.htw.saar.smartcity.aggregator.lib.properties.RawMicroserviceApplicationProperties;

import java.io.IOException;


public abstract class BaseReceiver extends BrokerConnection {

    protected final RawMeasurementHandler rawMeasurementHandler;

    public BaseReceiver(RawMicroserviceApplicationProperties applicationProperties, RawMeasurementHandler rawMeasurementHandler) {

        super(applicationProperties);

        this.rawMeasurementHandler = rawMeasurementHandler;

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
                rawMeasurementHandler.handleMessage(new SensorMeasurement(topic, message));
            };

            channel.basicConsume(applicationProperties.getMicroserviceQueue(), true, deliverCallback, consumerTag -> {});

        } catch (IOException e) {
            log.error("Error during base receiver channel instantiation.");
            e.printStackTrace();
        }
    }
}
