package de.htw.saar.smartcity.aggregator.dewpoint.storage;

import de.htw.saar.smartcity.aggregator.lib.properties.ApplicationProperties;
import de.htw.saar.smartcity.aggregator.lib.service.SensorService;
import de.htw.saar.smartcity.aggregator.lib.storage.StorageWrapper;
import org.springframework.stereotype.Component;

@Component
public class DewpointStorageWrapper extends StorageWrapper {

    public DewpointStorageWrapper(ApplicationProperties applicationProperties, SensorService sensorService) {
        super(applicationProperties, sensorService);
    }
}
