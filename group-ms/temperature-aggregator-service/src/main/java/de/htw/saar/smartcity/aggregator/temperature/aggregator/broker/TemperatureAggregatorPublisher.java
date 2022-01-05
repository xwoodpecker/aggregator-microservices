package de.htw.saar.smartcity.aggregator.temperature.aggregator.broker;

import de.htw.saar.smartcity.aggregator.lib.broker.AMQPPublisher;
import de.htw.saar.smartcity.aggregator.lib.properties.MicroserviceApplicationProperties;
import org.springframework.stereotype.Component;

@Component
public class TemperatureAggregatorPublisher extends AMQPPublisher {

    public TemperatureAggregatorPublisher(MicroserviceApplicationProperties applicationProperties) throws Exception {
        super(applicationProperties);
    }
}
