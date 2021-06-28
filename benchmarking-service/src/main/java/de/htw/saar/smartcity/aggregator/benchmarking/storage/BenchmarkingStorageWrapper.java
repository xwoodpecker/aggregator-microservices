package de.htw.saar.smartcity.aggregator.benchmarking.storage;

import de.htw.saar.smartcity.aggregator.lib.properties.MicroserviceApplicationProperties;
import de.htw.saar.smartcity.aggregator.lib.service.AggregatorService;
import de.htw.saar.smartcity.aggregator.lib.service.SensorService;
import de.htw.saar.smartcity.aggregator.lib.storage.StorageWrapper;
import org.springframework.stereotype.Component;

@Component
public class BenchmarkingStorageWrapper extends StorageWrapper {

    public BenchmarkingStorageWrapper(MicroserviceApplicationProperties applicationProperties, SensorService sensorService, AggregatorService aggregatorService) {
        super(applicationProperties, sensorService, aggregatorService);
    }

}
