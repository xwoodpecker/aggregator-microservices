package de.htw.saar.smartcity.aggregator.lib.broker;

import de.htw.saar.smartcity.aggregator.lib.handler.RawMeasurementHandler;
import de.htw.saar.smartcity.aggregator.lib.model.SensorMeasurement;
import de.htw.saar.smartcity.aggregator.lib.properties.RawMicroserviceApplicationProperties;


/**
 * The type Base receiver.
 */
public abstract class BaseReceiver extends Receiver {

    /**
     * The Raw measurement handler.
     */
    protected final RawMeasurementHandler rawMeasurementHandler;

    /**
     * Instantiates a new Base receiver.
     *
     * @param applicationProperties the application properties
     * @param activityManager       the activity manager
     * @param rawMeasurementHandler the raw measurement handler
     * @throws Exception the exception
     */
    public BaseReceiver(RawMicroserviceApplicationProperties applicationProperties,
                        ActivityManager activityManager,
                        RawMeasurementHandler rawMeasurementHandler) throws Exception {

        super(applicationProperties, activityManager);

        this.rawMeasurementHandler = rawMeasurementHandler;

        for(String topic : applicationProperties.getMicroserviceTopics()) {

            String routingKey = topic.replaceAll("/", ".");
            channel.queueBind(applicationProperties.getMicroserviceTopicName(), "amq.topic", routingKey);
        }
    }

    /**
     * process a message for a given routingKey
     *
     * @param routingKey the routing key
     * @param message    the message
     */
    void processMessage(String routingKey, String message) {

            String topic = routingKey.replaceAll("\\.", "/");
            rawMeasurementHandler.handleMessage(new SensorMeasurement(topic, message));
    }

}
