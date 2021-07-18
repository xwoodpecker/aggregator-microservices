package de.htw.saar.smartcity.aggregator.humidity.aggregator.storage;

import de.htw.saar.smartcity.aggregator.lib.properties.MicroserviceApplicationProperties;
import de.htw.saar.smartcity.aggregator.lib.service.AggregatorService;
import de.htw.saar.smartcity.aggregator.lib.service.SensorService;
import de.htw.saar.smartcity.aggregator.lib.storage.StorageWrapper;
import org.springframework.stereotype.Component;

@Component
public class HumidityAggregatorStorageWrapper extends StorageWrapper {

    public HumidityAggregatorStorageWrapper(MicroserviceApplicationProperties applicationProperties, SensorService sensorService, AggregatorService aggregatorService) throws Exception {
        super(applicationProperties, sensorService, aggregatorService);
    }
}
