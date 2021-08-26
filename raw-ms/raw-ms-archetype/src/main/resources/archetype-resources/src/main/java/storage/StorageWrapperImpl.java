package ${package}.storage;

import de.htw.saar.smartcity.aggregator.lib.service.AggregatorService;
import de.htw.saar.smartcity.aggregator.lib.service.SensorService;
import de.htw.saar.smartcity.aggregator.lib.storage.StorageWrapper;
import de.htw.saar.smartcity.aggregator.lib.properties.RawMicroserviceApplicationProperties;
import org.springframework.stereotype.Component;

@Component
public class StorageWrapperImpl extends StorageWrapper {

    public StorageWrapperImpl(RawMicroserviceApplicationProperties applicationProperties, SensorService sensorService, AggregatorService aggregatorService) throws Exception {
        super(applicationProperties, sensorService, aggregatorService);
    }

}
