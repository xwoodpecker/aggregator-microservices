package de.htw.saar.smartcity.aggregator.lib.broker;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.htw.saar.smartcity.aggregator.lib.base.Constants;
import de.htw.saar.smartcity.aggregator.lib.handler.GroupMeasurementHandler;
import de.htw.saar.smartcity.aggregator.lib.model.Measurement;
import de.htw.saar.smartcity.aggregator.lib.properties.GroupMicroserviceApplicationProperties;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;


/**
 * The type Group receiver.
 */
public abstract class GroupReceiver extends Receiver {

    /**
     * The Group measurement handler.
     */
    protected final GroupMeasurementHandler groupMeasurementHandler;


    /**
     * Instantiates a new Group receiver.
     *
     * @param applicationProperties   the application properties
     * @param activityManager         the activity manager
     * @param groupMeasurementHandler the group measurement handler
     * @throws Exception the exception
     */
    public GroupReceiver(GroupMicroserviceApplicationProperties applicationProperties,
                         ActivityManager activityManager,
                         GroupMeasurementHandler groupMeasurementHandler) throws Exception {

        super(applicationProperties, activityManager);
        this.groupMeasurementHandler = groupMeasurementHandler;

        String groupTypeName = applicationProperties.getGroupTypeName();
        channel.queueBind(applicationProperties.getMicroserviceTopicName(), Constants.GROUP_EXCHANGE, groupTypeName + ".#");
    }

    /**
     * process the message for the given routingKey
     *
     * @param routingKey the routing key
     * @param message    the message
     */
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
