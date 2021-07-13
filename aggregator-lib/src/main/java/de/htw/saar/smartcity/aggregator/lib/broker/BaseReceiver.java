package de.htw.saar.smartcity.aggregator.lib.broker;

import de.htw.saar.smartcity.aggregator.lib.handler.RawMeasurementHandler;
import de.htw.saar.smartcity.aggregator.lib.model.SensorMeasurement;
import de.htw.saar.smartcity.aggregator.lib.properties.RawMicroserviceApplicationProperties;

import java.io.IOException;


public abstract class BaseReceiver extends Receiver {

    protected final RawMeasurementHandler rawMeasurementHandler;

    public BaseReceiver(RawMicroserviceApplicationProperties applicationProperties,
                        ActivityManager activityManager,
                        RawMeasurementHandler rawMeasurementHandler) throws Exception {

        super(applicationProperties, activityManager);

        this.rawMeasurementHandler = rawMeasurementHandler;

        for(String topic : applicationProperties.getMicroserviceTopics()) {

            String routingKey = topic.replaceAll("/", ".");
            channel.queueBind(applicationProperties.getMicroserviceQueue(), "amq.topic", routingKey);
        }
    }

    void processMessage(String routingKey, String message) {

            String topic = routingKey.replaceAll("\\.", "/");
            rawMeasurementHandler.handleMessage(new SensorMeasurement(topic, message));
    }

}
