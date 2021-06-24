package de.htw.saar.smartcity.aggregator.temperature.broker;

import de.htw.saar.smartcity.aggregator.lib.broker.Publisher;
import de.htw.saar.smartcity.aggregator.lib.properties.MicroserviceApplicationProperties;
import org.springframework.stereotype.Component;

@Component
public class TemperaturePublisher extends Publisher {

    public TemperaturePublisher(MicroserviceApplicationProperties applicationProperties) {
        super(applicationProperties);
    }
}
