package de.htw.saar.smartcity.aggregator.lib.broker;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.htw.saar.smartcity.aggregator.lib.base.Constants;
import de.htw.saar.smartcity.aggregator.lib.handler.GroupMeasurementHandler;
import de.htw.saar.smartcity.aggregator.lib.model.Measurement;
import de.htw.saar.smartcity.aggregator.lib.properties.GroupMicroserviceApplicationProperties;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;


public abstract class GroupReceiver extends Receiver {

    protected final GroupMeasurementHandler groupMeasurementHandler;


    public GroupReceiver(GroupMicroserviceApplicationProperties applicationProperties,
                         ActivityManager activityManager,
                         GroupMeasurementHandler groupMeasurementHandler) throws Exception {

        super(applicationProperties, activityManager);
        this.groupMeasurementHandler = groupMeasurementHandler;

        String groupTypeName = applicationProperties.getMicroserviceGroupTypeName();
        channel.queueBind(applicationProperties.getMicroserviceQueue(), Constants.GROUP_EXCHANGE, groupTypeName + ".#");
    }

    void processMessage(String routingKey, String message) {


        String[] parts = routingKey.split("\\.");
        if (parts != null & parts.length == 3) {


            Measurement measurement;
            try {
                try (InputStream is = URI.create(message).toURL().openConnection().getInputStream()) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    measurement = objectMapper.readValue(is.readAllBytes(), Measurement.class);
                }

            } catch (IOException ex) {
                log.error("Could not read measurement from given URL");
                return;
            }

            try {
                Long groupId = Long.valueOf(parts[1]);
                Long producerId = Long.valueOf(parts[2]);

                groupMeasurementHandler.handleMeasurement(groupId, producerId, measurement);

            } catch (NumberFormatException e) {
                log.error("URL not published with the correct routing key");

            }

        } else {
            log.error("URL not published with the correct routing key");
        }
    }

}
