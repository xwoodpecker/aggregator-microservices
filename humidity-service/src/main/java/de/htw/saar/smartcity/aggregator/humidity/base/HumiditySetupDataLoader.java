package de.htw.saar.smartcity.aggregator.humidity.base;

import de.htw.saar.smartcity.aggregator.lib.base.BaseSetupDataLoader;
import de.htw.saar.smartcity.aggregator.lib.properties.BaseMicroserviceApplicationProperties;
import de.htw.saar.smartcity.aggregator.lib.service.SensorTypeService;
import org.springframework.stereotype.Component;

/**
 * The type Setup data loader.
 */
@Component
public class HumiditySetupDataLoader extends BaseSetupDataLoader {

    public HumiditySetupDataLoader(SensorTypeService sensorTypeService, BaseMicroserviceApplicationProperties baseMicroserviceApplicationProperties) {
        super(sensorTypeService, baseMicroserviceApplicationProperties);
    }
}
