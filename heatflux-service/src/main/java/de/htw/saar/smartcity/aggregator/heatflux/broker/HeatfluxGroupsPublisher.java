package de.htw.saar.smartcity.aggregator.heatflux.broker;

import de.htw.saar.smartcity.aggregator.lib.broker.Publisher;
import de.htw.saar.smartcity.aggregator.lib.properties.MicroserviceApplicationProperties;
import org.springframework.stereotype.Component;

@Component
public class HeatfluxGroupsPublisher extends Publisher {

    public HeatfluxGroupsPublisher(MicroserviceApplicationProperties applicationProperties) {
        super(applicationProperties);
    }
}
