package de.htw.saar.smartcity.aggregator.temperature.base;

import de.htw.saar.smartcity.aggregator.lib.base.BaseSetupDataLoader;
import de.htw.saar.smartcity.aggregator.lib.service.SensorTypeService;
import de.htw.saar.smartcity.aggregator.lib.properties.BaseMicroserviceApplicationProperties;
import de.htw.saar.smartcity.aggregator.lib.service.MicroserviceService;
import org.springframework.stereotype.Component;

/**
 * The type Setup data loader.
 */
@Component
public class TemperatureSetupDataLoader extends BaseSetupDataLoader {

    public TemperatureSetupDataLoader(MicroserviceService microserviceService, SensorTypeService sensorTypeService, BaseMicroserviceApplicationProperties baseMicroserviceApplicationProperties) {
        super(microserviceService, sensorTypeService, baseMicroserviceApplicationProperties);
    }
}
