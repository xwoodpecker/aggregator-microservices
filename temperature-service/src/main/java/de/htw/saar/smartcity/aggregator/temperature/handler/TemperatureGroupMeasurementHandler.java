package de.htw.saar.smartcity.aggregator.temperature.handler;

import de.htw.saar.smartcity.aggregator.handler.GroupMeasurementHandler;
import de.htw.saar.smartcity.aggregator.model.Combinators;
import de.htw.saar.smartcity.aggregator.storage.StorageWrapper;
import de.htw.saar.smartcity.aggregator.temperature.factory.TemperatureMeasurementFactory;
import org.springframework.stereotype.Component;

@Component
public class TemperatureGroupMeasurementHandler extends GroupMeasurementHandler {

    public TemperatureGroupMeasurementHandler(TemperatureMeasurementFactory temperatureMeasurementFactory, StorageWrapper storageWrapper) {
        super(temperatureMeasurementFactory, storageWrapper);
    }

    @Override
    protected void addCombinators() {

        combinators.add(Combinators.AverageCombinator);
        combinators.add(Combinators.MinimumCombinator);
        combinators.add(Combinators.MaximumCombinator);
    }
}
