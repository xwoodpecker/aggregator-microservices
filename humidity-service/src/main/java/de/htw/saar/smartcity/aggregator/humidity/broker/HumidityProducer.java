package de.htw.saar.smartcity.aggregator.humidity.broker;

import de.htw.saar.smartcity.aggregator.lib.broker.Producer;
import de.htw.saar.smartcity.aggregator.lib.properties.ApplicationProperties;
import org.springframework.stereotype.Component;

@Component
public class HumidityProducer extends Producer {

    public HumidityProducer(ApplicationProperties applicationProperties) {
        super(applicationProperties);
    }
}
