package de.htw.saar.smartcity.aggregator.temperature.handler;

import de.htw.saar.smartcity.aggregator.handler.MeasurementHandler;
import de.htw.saar.smartcity.aggregator.temperature.factory.TemperatureMeasurementFactory;
import de.htw.saar.smartcity.aggregator.temperature.storage.TemperatureStorageWrapper;
import org.springframework.stereotype.Component;

@Component
public class TemperatureMeasurementHandler extends MeasurementHandler {

    public TemperatureMeasurementHandler(TemperatureMeasurementFactory temperatureMeasurementFactory, TemperatureStorageWrapper temperatureStorageWrapper) {
        super(temperatureMeasurementFactory, temperatureStorageWrapper);
    }
}
