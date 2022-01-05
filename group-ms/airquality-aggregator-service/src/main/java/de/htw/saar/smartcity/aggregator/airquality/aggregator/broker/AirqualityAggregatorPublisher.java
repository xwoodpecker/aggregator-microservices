package de.htw.saar.smartcity.aggregator.airquality.aggregator.broker;

import de.htw.saar.smartcity.aggregator.lib.broker.AMQPPublisher;
import de.htw.saar.smartcity.aggregator.lib.properties.MicroserviceApplicationProperties;
import org.springframework.stereotype.Component;

@Component
public class AirqualityAggregatorPublisher extends AMQPPublisher {

    public AirqualityAggregatorPublisher(MicroserviceApplicationProperties applicationProperties) throws Exception {
        super(applicationProperties);
    }
}
