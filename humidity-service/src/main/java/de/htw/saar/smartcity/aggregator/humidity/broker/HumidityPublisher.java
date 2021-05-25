package de.htw.saar.smartcity.aggregator.humidity.broker;

import de.htw.saar.smartcity.aggregator.lib.broker.Publisher;
import de.htw.saar.smartcity.aggregator.lib.properties.ApplicationProperties;
import org.springframework.stereotype.Component;

@Component
public class HumidityPublisher extends Publisher {

    public HumidityPublisher(ApplicationProperties applicationProperties) {
        super(applicationProperties);
    }
}
