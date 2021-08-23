package de.htw.saar.smartcity.sensor.app.broker;

import de.htw.saar.smartcity.aggregator.lib.broker.MqttPublisher;
import de.htw.saar.smartcity.aggregator.lib.properties.BrokerApplicationProperties;
import org.springframework.stereotype.Component;

/**
 * The type Mqtt publisher.
 */
@Component
public class MqttPublisherImpl extends MqttPublisher {

    /**
     * Instantiates a new Mqtt publisher.
     *
     * @param applicationProperties the application properties
     * @throws Exception the exception
     */
    public MqttPublisherImpl(BrokerApplicationProperties applicationProperties) throws Exception {
        super(applicationProperties);
    }
}
