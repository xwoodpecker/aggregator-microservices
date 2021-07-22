package de.htw.saar.smartcity.aggregator.airquality.storage;

import de.htw.saar.smartcity.aggregator.lib.service.AggregatorService;
import de.htw.saar.smartcity.aggregator.lib.service.SensorService;
import de.htw.saar.smartcity.aggregator.lib.storage.StorageWrapper;
import de.htw.saar.smartcity.aggregator.airquality.properties.AirqualityApplicationProperties;
import org.springframework.stereotype.Component;

@Component
public class AirqualityStorageWrapper extends StorageWrapper {

    public AirqualityStorageWrapper(AirqualityApplicationProperties applicationProperties, SensorService sensorService, AggregatorService aggregatorService) throws Exception {
        super(applicationProperties, sensorService, aggregatorService);
    }

}
