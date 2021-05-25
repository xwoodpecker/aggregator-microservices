package de.htw.saar.smartcity.aggregator.temperature.broker;

import de.htw.saar.smartcity.aggregator.lib.broker.Producer;
import de.htw.saar.smartcity.aggregator.lib.properties.ApplicationProperties;
import org.springframework.stereotype.Component;

@Component
public class TemperatureProducer extends Producer {

    public TemperatureProducer(ApplicationProperties applicationProperties) {
        super(applicationProperties);
    }
}
