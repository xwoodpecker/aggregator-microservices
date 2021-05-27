package de.htw.saar.smartcity.aggregator.lib.broker;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import de.htw.saar.smartcity.aggregator.lib.handler.GroupMeasurementHandler;
import de.htw.saar.smartcity.aggregator.lib.model.Measurement;
import de.htw.saar.smartcity.aggregator.lib.properties.ApplicationProperties;
import de.htw.saar.smartcity.aggregator.lib.properties.GroupMicroserviceApplicationProperties;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;


public abstract class GroupsReceiver extends BrokerConnection {

    protected final GroupMeasurementHandler groupMeasurementHandler;


    public GroupsReceiver(ApplicationProperties applicationProperties, GroupMeasurementHandler groupMeasurementHandler) {

        super(applicationProperties);
        this.groupMeasurementHandler = groupMeasurementHandler;

        Channel channel;

        try {
            channel = connection.createChannel();

            channel.queueDeclare(applicationProperties.getMicroserviceQueue(), true, false, false, null);

            //todo refactor
            String groupTypeName = null;
            if(applicationProperties instanceof GroupMicroserviceApplicationProperties)
                groupTypeName = ((GroupMicroserviceApplicationProperties)applicationProperties).getMicroserviceGroupTypeName();


            channel.queueBind(applicationProperties.getMicroserviceQueue(), "group_exchange", groupTypeName + ".#");

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {

                String url = new String(delivery.getBody(), "UTF-8");
                String routingKey = delivery.getEnvelope().getRoutingKey();
                String[] parts = routingKey.split("\\.");
                if(parts != null & parts.length == 3) {

                    Long groupId = Long.valueOf(parts[1]);
                    Long producerId = Long.valueOf(parts[2]);

                    InputStream is = URI.create(url).toURL().openConnection().getInputStream();
                    ObjectMapper objectMapper = new ObjectMapper();
                    Measurement measurement = objectMapper.readValue(is.readAllBytes(), Measurement.class);

                    groupMeasurementHandler.handleMeasurement(groupId, producerId, measurement);
                }

            };

            channel.basicConsume(applicationProperties.getMicroserviceQueue(), true, deliverCallback, consumerTag -> {});

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}