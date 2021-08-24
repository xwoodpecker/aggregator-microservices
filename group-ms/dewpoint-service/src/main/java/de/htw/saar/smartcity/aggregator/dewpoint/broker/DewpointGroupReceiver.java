package de.htw.saar.smartcity.aggregator.dewpoint.broker;

import de.htw.saar.smartcity.aggregator.lib.broker.ActivityManager;
import de.htw.saar.smartcity.aggregator.lib.broker.GroupReceiver;
import de.htw.saar.smartcity.aggregator.lib.handler.GroupMeasurementHandler;
import de.htw.saar.smartcity.aggregator.lib.properties.GroupMicroserviceApplicationProperties;
import org.springframework.stereotype.Component;

@Component
public class DewpointGroupReceiver extends GroupReceiver {

    public DewpointGroupReceiver(GroupMicroserviceApplicationProperties applicationProperties, ActivityManager activityManager, GroupMeasurementHandler groupMeasurementHandler) throws Exception {
        super(applicationProperties, activityManager, groupMeasurementHandler);
    }
}
