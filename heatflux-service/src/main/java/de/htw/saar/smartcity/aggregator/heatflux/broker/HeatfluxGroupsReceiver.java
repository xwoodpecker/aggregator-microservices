package de.htw.saar.smartcity.aggregator.heatflux.broker;

import de.htw.saar.smartcity.aggregator.lib.broker.GroupsReceiver;
import de.htw.saar.smartcity.aggregator.lib.handler.GroupMeasurementHandler;
import de.htw.saar.smartcity.aggregator.lib.properties.GroupMicroserviceApplicationProperties;
import org.springframework.stereotype.Component;

@Component
public class HeatfluxGroupsReceiver extends GroupsReceiver {

    public HeatfluxGroupsReceiver(GroupMicroserviceApplicationProperties applicationProperties, GroupMeasurementHandler groupMeasurementHandler) {
        super(applicationProperties, groupMeasurementHandler);
    }
}
