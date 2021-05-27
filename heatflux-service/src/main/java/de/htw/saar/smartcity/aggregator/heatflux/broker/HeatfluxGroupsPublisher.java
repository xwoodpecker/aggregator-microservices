package de.htw.saar.smartcity.aggregator.heatflux.broker;

import de.htw.saar.smartcity.aggregator.lib.broker.Publisher;
import de.htw.saar.smartcity.aggregator.lib.properties.ApplicationProperties;
import org.springframework.stereotype.Component;

@Component
public class HeatfluxGroupsPublisher extends Publisher {

    public HeatfluxGroupsPublisher(ApplicationProperties applicationProperties) {
        super(applicationProperties);
    }
}
