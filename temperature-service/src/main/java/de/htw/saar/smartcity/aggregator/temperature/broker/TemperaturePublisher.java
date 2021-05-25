package de.htw.saar.smartcity.aggregator.temperature.broker;

import de.htw.saar.smartcity.aggregator.lib.broker.Publisher;
import de.htw.saar.smartcity.aggregator.lib.properties.ApplicationProperties;
import org.springframework.stereotype.Component;

@Component
public class TemperaturePublisher extends Publisher {

    public TemperaturePublisher(ApplicationProperties applicationProperties) {
        super(applicationProperties);
    }
}
