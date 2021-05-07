package de.htw.saar.smartcity.aggregator.humidity.handler;

import de.htw.saar.smartcity.aggregator.factory.MeasurementFactory;
import de.htw.saar.smartcity.aggregator.handler.MeasurementHandler;
import de.htw.saar.smartcity.aggregator.storage.StorageWrapper;
import org.springframework.stereotype.Component;

@Component
public class HumidityMeasurementHandler extends MeasurementHandler {

    public HumidityMeasurementHandler(MeasurementFactory measurementFactory, StorageWrapper storageWrapper) {
        super(measurementFactory, storageWrapper);
    }
}
