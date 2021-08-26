package ${package}.broker;

import de.htw.saar.smartcity.aggregator.lib.broker.Publisher;
import de.htw.saar.smartcity.aggregator.lib.properties.MicroserviceApplicationProperties;
import org.springframework.stereotype.Component;

@Component
public class PublisherImpl extends Publisher {

    public PublisherImpl(MicroserviceApplicationProperties applicationProperties) throws Exception {
        super(applicationProperties);
    }
}
