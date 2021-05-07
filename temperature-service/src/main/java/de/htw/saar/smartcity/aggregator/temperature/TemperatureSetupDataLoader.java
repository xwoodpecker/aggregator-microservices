package de.htw.saar.smartcity.aggregator.temperature;

import de.htw.saar.smartcity.aggregator.base.SetupDataLoader;
import de.htw.saar.smartcity.aggregator.service.MicroserviceService;
import de.htw.saar.smartcity.aggregator.temperature.properties.TemperatureApplicationProperties;
import org.springframework.stereotype.Component;

/**
 * The type Setup data loader.
 */
@Component
public class TemperatureSetupDataLoader extends SetupDataLoader {

    public TemperatureSetupDataLoader(MicroserviceService microserviceService, TemperatureApplicationProperties temperatureApplicationProperties) {
        super(microserviceService, temperatureApplicationProperties);
    }
}
