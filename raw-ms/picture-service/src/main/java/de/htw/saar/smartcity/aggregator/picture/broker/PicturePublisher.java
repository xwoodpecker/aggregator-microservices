package de.htw.saar.smartcity.aggregator.picture.broker;

import de.htw.saar.smartcity.aggregator.lib.broker.AMQPPublisher;
import de.htw.saar.smartcity.aggregator.lib.properties.MicroserviceApplicationProperties;
import org.springframework.stereotype.Component;

@Component
public class PicturePublisher extends AMQPPublisher {

    public PicturePublisher(MicroserviceApplicationProperties applicationProperties) throws Exception {
        super(applicationProperties);
    }
}
