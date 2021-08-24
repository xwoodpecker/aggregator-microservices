package de.htw.saar.smartcity.aggregator.temperature.storage;

import de.htw.saar.smartcity.aggregator.lib.service.AggregatorService;
import de.htw.saar.smartcity.aggregator.lib.service.SensorService;
import de.htw.saar.smartcity.aggregator.temperature.properties.TemperatureApplicationProperties;
import de.htw.saar.smartcity.aggregator.lib.storage.StorageWrapper;
import org.springframework.stereotype.Component;

@Component
public class TemperatureStorageWrapper extends StorageWrapper {

    public TemperatureStorageWrapper(TemperatureApplicationProperties applicationProperties, SensorService sensorService, AggregatorService aggregatorService) throws Exception {
        super(applicationProperties, sensorService, aggregatorService);
    }

}
