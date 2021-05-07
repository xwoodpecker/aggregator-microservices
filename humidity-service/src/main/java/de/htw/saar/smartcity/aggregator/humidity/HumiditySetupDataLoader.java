package de.htw.saar.smartcity.aggregator.humidity;

import de.htw.saar.smartcity.aggregator.base.SetupDataLoader;
import de.htw.saar.smartcity.aggregator.properties.MicroserviceApplicationProperties;
import de.htw.saar.smartcity.aggregator.service.MicroserviceService;
import org.springframework.stereotype.Component;

/**
 * The type Setup data loader.
 */
@Component
public class HumiditySetupDataLoader extends SetupDataLoader {

    public HumiditySetupDataLoader(MicroserviceService microserviceService, MicroserviceApplicationProperties microserviceApplicationProperties) {
        super(microserviceService, microserviceApplicationProperties);
    }
}
