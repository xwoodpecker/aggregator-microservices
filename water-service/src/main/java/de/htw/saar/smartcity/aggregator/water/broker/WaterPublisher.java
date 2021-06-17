package de.htw.saar.smartcity.aggregator.water.broker;

import de.htw.saar.smartcity.aggregator.lib.broker.Publisher;
import de.htw.saar.smartcity.aggregator.lib.properties.MicroserviceApplicationProperties;
import org.springframework.stereotype.Component;

@Component
public class WaterPublisher extends Publisher {

    public WaterPublisher(MicroserviceApplicationProperties applicationProperties) {
        super(applicationProperties);
    }
}
