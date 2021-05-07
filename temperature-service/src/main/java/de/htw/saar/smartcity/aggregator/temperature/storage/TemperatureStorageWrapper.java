package de.htw.saar.smartcity.aggregator.temperature.storage;

import de.htw.saar.smartcity.aggregator.service.SensorService;
import de.htw.saar.smartcity.aggregator.storage.StorageWrapper;
import de.htw.saar.smartcity.aggregator.temperature.properties.TemperatureApplicationProperties;
import org.springframework.stereotype.Component;

@Component
public class TemperatureStorageWrapper extends StorageWrapper {

    public TemperatureStorageWrapper(TemperatureApplicationProperties applicationProperties, SensorService sensorService) {
        super(applicationProperties, sensorService);
    }

}
