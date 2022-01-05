package de.htw.saar.smartcity.aggregator.temperature.broker;

import de.htw.saar.smartcity.aggregator.lib.broker.AMQPPublisher;
import de.htw.saar.smartcity.aggregator.lib.properties.MicroserviceApplicationProperties;
import org.springframework.stereotype.Component;

@Component
public class TemperaturePublisher extends AMQPPublisher {

    public TemperaturePublisher(MicroserviceApplicationProperties applicationProperties) throws Exception {
        super(applicationProperties);
    }
}
