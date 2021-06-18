package de.htw.saar.smartcity.aggregator.temperature.aggregator.broker;

import de.htw.saar.smartcity.aggregator.lib.broker.GroupsReceiver;
import de.htw.saar.smartcity.aggregator.lib.handler.GroupMeasurementHandler;
import de.htw.saar.smartcity.aggregator.lib.properties.GroupMicroserviceApplicationProperties;
import org.springframework.stereotype.Component;

@Component
public class TemperatureAggregatorGroupsReceiver extends GroupsReceiver {

    public TemperatureAggregatorGroupsReceiver(GroupMicroserviceApplicationProperties applicationProperties, GroupMeasurementHandler groupMeasurementHandler) {
        super(applicationProperties, groupMeasurementHandler);
    }
}
