package de.htw.saar.smartcity.aggregator.humidity.storage;

import de.htw.saar.smartcity.aggregator.lib.properties.MicroserviceApplicationProperties;
import de.htw.saar.smartcity.aggregator.lib.service.SensorService;
import de.htw.saar.smartcity.aggregator.lib.storage.StorageWrapper;
import org.springframework.stereotype.Component;

@Component
public class HumidityStorageWrapper extends StorageWrapper {

    public HumidityStorageWrapper(MicroserviceApplicationProperties applicationProperties, SensorService sensorService) {
        super(applicationProperties, sensorService, aggregatorService);
    }
}
