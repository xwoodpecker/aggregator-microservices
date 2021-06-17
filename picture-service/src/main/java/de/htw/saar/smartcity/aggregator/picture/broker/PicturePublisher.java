package de.htw.saar.smartcity.aggregator.picture.broker;

import de.htw.saar.smartcity.aggregator.lib.broker.Publisher;
import de.htw.saar.smartcity.aggregator.lib.properties.MicroserviceApplicationProperties;
import org.springframework.stereotype.Component;

@Component
public class PicturePublisher extends Publisher {

    public PicturePublisher(MicroserviceApplicationProperties applicationProperties) {
        super(applicationProperties);
    }
}
