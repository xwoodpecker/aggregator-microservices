package de.htw.saar.smartcity.aggregator.lib.broker;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;
import de.htw.saar.smartcity.aggregator.lib.handler.RawMeasurementHandler;
import de.htw.saar.smartcity.aggregator.lib.model.SensorMeasurement;
import de.htw.saar.smartcity.aggregator.lib.properties.RawMicroserviceApplicationProperties;

import java.io.IOException;


public abstract class BaseReceiver extends BrokerConnection {

    protected final RawMeasurementHandler rawMeasurementHandler;

    public BaseReceiver(RawMicroserviceApplicationProperties applicationProperties, RawMeasurementHandler rawMeasurementHandler) {

        super(applicationProperties);

        this.rawMeasurementHandler = rawMeasurementHandler;

        try {
            channel.queueDeclare(applicationProperties.getMicroserviceQueue(), true, false, false, null);

            for(String topic : applicationProperties.getMicroserviceTopics()) {

                String routingKey = topic.replaceAll("/", ".");
                channel.queueBind(applicationProperties.getMicroserviceQueue(), "amq.topic", routingKey);
            }

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {

                try {
                    //systemzeit rein, speichern in datenstruktur anderer thread
                    String message = new String(delivery.getBody(), "UTF-8");
                    String routingKey = delivery.getEnvelope().getRoutingKey();
                    String topic = routingKey.replaceAll("\\.", "/");
                    rawMeasurementHandler.handleMessage(new SensorMeasurement(topic, message));

                }catch (Exception e) {
                    log.error("Error during consumption of measurement");
                    e.printStackTrace();
                }


                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false); //test true to ack multiple deliveries
                //systemzeit raus
            };

            channel.basicConsume(applicationProperties.getMicroserviceQueue(), false, deliverCallback, consumerTag -> {});

        } catch (IOException e) {
            log.error("Error during base receiver channel instantiation.");
            e.printStackTrace();
        }
    }

}
