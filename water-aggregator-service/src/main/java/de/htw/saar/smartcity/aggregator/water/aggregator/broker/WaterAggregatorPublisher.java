package de.htw.saar.smartcity.aggregator.water.aggregator.broker;

import de.htw.saar.smartcity.aggregator.lib.broker.Publisher;
import de.htw.saar.smartcity.aggregator.lib.properties.MicroserviceApplicationProperties;
import org.springframework.stereotype.Component;

@Component
public class WaterAggregatorPublisher extends Publisher {

    public WaterAggregatorPublisher(MicroserviceApplicationProperties applicationProperties) throws Exception {
        super(applicationProperties);
    }
}
