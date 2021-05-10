package de.htw.saar.smartcity.aggregator.temperature.handler;

import de.htw.saar.smartcity.aggregator.lib.factory.MeasurementFactory;
import de.htw.saar.smartcity.aggregator.lib.handler.BaseGroupMeasurementHandler;
import de.htw.saar.smartcity.aggregator.lib.model.GroupCombinators;
import de.htw.saar.smartcity.aggregator.lib.storage.StorageWrapper;
import org.springframework.stereotype.Component;

@Component
public class TemperatureBaseGroupMeasurementHandler extends BaseGroupMeasurementHandler {


    public TemperatureBaseGroupMeasurementHandler(MeasurementFactory measurementFactory, StorageWrapper storageWrapper) {
        super(measurementFactory, storageWrapper);
    }

    @Override
    protected void addCombinators() {

        groupCombinators.add(GroupCombinators.averageGroupCombinator);
        groupCombinators.add(GroupCombinators.minimumGroupCombinator);
        groupCombinators.add(GroupCombinators.maximumGroupCombinator);
    }
}
