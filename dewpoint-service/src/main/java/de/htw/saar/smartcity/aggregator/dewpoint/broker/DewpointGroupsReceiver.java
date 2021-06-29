package de.htw.saar.smartcity.aggregator.dewpoint.broker;

import de.htw.saar.smartcity.aggregator.lib.broker.ActivityManager;
import de.htw.saar.smartcity.aggregator.lib.broker.GroupsReceiver;
import de.htw.saar.smartcity.aggregator.lib.handler.GroupMeasurementHandler;
import de.htw.saar.smartcity.aggregator.lib.properties.GroupMicroserviceApplicationProperties;
import org.springframework.stereotype.Component;

@Component
public class DewpointGroupsReceiver extends GroupsReceiver {

    public DewpointGroupsReceiver(GroupMicroserviceApplicationProperties applicationProperties, ActivityManager activityManager, GroupMeasurementHandler groupMeasurementHandler) {
        super(applicationProperties, activityManager, groupMeasurementHandler);
    }
}
