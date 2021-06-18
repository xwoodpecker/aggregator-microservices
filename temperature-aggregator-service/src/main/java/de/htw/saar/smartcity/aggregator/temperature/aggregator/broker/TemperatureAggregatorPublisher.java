package de.htw.saar.smartcity.aggregator.temperature.aggregator.broker;

import de.htw.saar.smartcity.aggregator.lib.broker.Publisher;
import de.htw.saar.smartcity.aggregator.lib.properties.MicroserviceApplicationProperties;
import org.springframework.stereotype.Component;

@Component
public class TemperatureAggregatorPublisher extends Publisher {

    public TemperatureAggregatorPublisher(MicroserviceApplicationProperties applicationProperties) {
        super(applicationProperties);
    }
}
