package de.htw.saar.smartcity.aggregator.airquality.broker;

import de.htw.saar.smartcity.aggregator.lib.broker.AMQPPublisher;
import de.htw.saar.smartcity.aggregator.lib.properties.MicroserviceApplicationProperties;
import org.springframework.stereotype.Component;

@Component
public class AirqualityPublisher extends AMQPPublisher {

    public AirqualityPublisher(MicroserviceApplicationProperties applicationProperties) throws Exception {
        super(applicationProperties);
    }
}
