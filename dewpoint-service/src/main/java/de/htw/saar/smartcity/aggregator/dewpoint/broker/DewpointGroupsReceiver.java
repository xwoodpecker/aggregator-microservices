package de.htw.saar.smartcity.aggregator.dewpoint.broker;

import de.htw.saar.smartcity.aggregator.lib.broker.GroupsReceiver;
import de.htw.saar.smartcity.aggregator.lib.handler.GroupMeasurementHandler;
import de.htw.saar.smartcity.aggregator.lib.properties.ApplicationProperties;
import org.springframework.stereotype.Component;

@Component
public class DewpointGroupsReceiver extends GroupsReceiver {

    public DewpointGroupsReceiver(ApplicationProperties applicationProperties, GroupMeasurementHandler groupMeasurementHandler) {
        super(applicationProperties, groupMeasurementHandler);
    }
}
