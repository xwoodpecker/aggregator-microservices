package de.htw.saar.smartcity.aggregator.humidity.aggregator.broker;

import de.htw.saar.smartcity.aggregator.lib.broker.Publisher;
import de.htw.saar.smartcity.aggregator.lib.properties.MicroserviceApplicationProperties;
import org.springframework.stereotype.Component;

@Component
public class HumidityAggregatorPublisher extends Publisher {

    public HumidityAggregatorPublisher(MicroserviceApplicationProperties applicationProperties) throws Exception {
        super(applicationProperties);
    }
}
