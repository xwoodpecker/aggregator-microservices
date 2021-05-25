package de.htw.saar.smartcity.aggregator.temperature.base;

import de.htw.saar.smartcity.aggregator.lib.base.RawSetupDataLoader;
import de.htw.saar.smartcity.aggregator.lib.service.DataTypeService;
import de.htw.saar.smartcity.aggregator.lib.properties.RawMicroserviceApplicationProperties;
import org.springframework.stereotype.Component;

/**
 * The type Setup data loader.
 */
@Component
public class TemperatureSetupDataLoader extends RawSetupDataLoader {

    public TemperatureSetupDataLoader(DataTypeService dataTypeService, RawMicroserviceApplicationProperties rawMicroserviceApplicationProperties) {
        super(dataTypeService, rawMicroserviceApplicationProperties);
    }
}
