package $ import de.htw.saar.smartcity.aggregator.lib.properties.MicroserviceApplicationProperties;
import org.springframework.stereotype.Component;

{package}.broker;

@Component
public class PublisherImpl extends AMQPPublisher {

    public PublisherImpl(MicroserviceApplicationProperties applicationProperties) throws Exception {
        super(applicationProperties);
    }
}
