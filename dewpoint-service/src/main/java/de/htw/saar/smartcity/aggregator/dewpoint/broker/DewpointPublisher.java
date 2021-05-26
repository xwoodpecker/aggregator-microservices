package de.htw.saar.smartcity.aggregator.dewpoint.broker;

import de.htw.saar.smartcity.aggregator.lib.broker.Publisher;
import de.htw.saar.smartcity.aggregator.lib.properties.ApplicationProperties;
import org.springframework.stereotype.Component;

@Component
public class DewpointPublisher extends Publisher {

    public DewpointPublisher(ApplicationProperties applicationProperties) {
        super(applicationProperties);
    }
}
