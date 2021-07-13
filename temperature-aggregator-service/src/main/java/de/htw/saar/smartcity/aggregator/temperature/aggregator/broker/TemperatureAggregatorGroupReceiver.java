package de.htw.saar.smartcity.aggregator.temperature.aggregator.broker;

import de.htw.saar.smartcity.aggregator.lib.broker.ActivityManager;
import de.htw.saar.smartcity.aggregator.lib.broker.GroupReceiver;
import de.htw.saar.smartcity.aggregator.lib.handler.GroupMeasurementHandler;
import de.htw.saar.smartcity.aggregator.lib.properties.GroupMicroserviceApplicationProperties;
import org.springframework.stereotype.Component;

@Component
public class TemperatureAggregatorGroupReceiver extends GroupReceiver {

    public TemperatureAggregatorGroupReceiver(GroupMicroserviceApplicationProperties applicationProperties, ActivityManager activityManager, GroupMeasurementHandler groupMeasurementHandler) {
        super(applicationProperties, activityManager, groupMeasurementHandler);
    }
}
