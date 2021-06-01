package de.htw.saar.smartcity.aggregator.lib.broker;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import de.htw.saar.smartcity.aggregator.lib.base.Constants;
import de.htw.saar.smartcity.aggregator.lib.handler.GroupMeasurementHandler;
import de.htw.saar.smartcity.aggregator.lib.model.Measurement;
import de.htw.saar.smartcity.aggregator.lib.properties.GroupMicroserviceApplicationProperties;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;


public abstract class GroupsReceiver extends BrokerConnection {

    protected final GroupMeasurementHandler groupMeasurementHandler;


    public GroupsReceiver(GroupMicroserviceApplicationProperties applicationProperties, GroupMeasurementHandler groupMeasurementHandler) {

        super(applicationProperties);
        this.groupMeasurementHandler = groupMeasurementHandler;

        Channel channel;

        try {
            channel = connection.createChannel();

            channel.queueDeclare(applicationProperties.getMicroserviceQueue(), true, false, false, null);

            String groupTypeName = applicationProperties.getMicroserviceGroupTypeName();


            channel.queueBind(applicationProperties.getMicroserviceQueue(), Constants.GROUP_EXCHANGE, groupTypeName + ".#");

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {

                String url = new String(delivery.getBody(), "UTF-8");
                String routingKey = delivery.getEnvelope().getRoutingKey();
                String[] parts = routingKey.split("\\.");
                if(parts != null & parts.length == 3) {

                        InputStream is;
                        try {
                             is = URI.create(url).toURL().openConnection().getInputStream();
                        } catch (IOException ex) {
                            log.error("Could not read measurement from given URL");
                            return;
                        }
                        ObjectMapper objectMapper = new ObjectMapper();
                        Measurement measurement = objectMapper.readValue(is.readAllBytes(), Measurement.class);

                    try {
                        Long groupId = Long.valueOf(parts[1]);
                        Long producerId = Long.valueOf(parts[2]);

                        groupMeasurementHandler.handleMeasurement(groupId, producerId, measurement);

                    } catch (NumberFormatException e) {
                        log.error("URL not published with the correct routing key");

                    }  catch (Exception e) {
                        log.error("Error during consumption of measurement");
                        e.printStackTrace();
                    }

                }else {
                    log.error("URL not published with the correct routing key");
                }

            };

            channel.basicConsume(applicationProperties.getMicroserviceQueue(), true, deliverCallback, consumerTag -> {});

        } catch (IOException e) {
            log.error("Error during groups receiver channel instantiation.");
            e.printStackTrace();
        }
    }
}
