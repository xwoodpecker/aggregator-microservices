package de.htw.saar.smartcity.aggregator.lib.broker;

import de.htw.saar.smartcity.aggregator.lib.properties.BrokerApplicationProperties;

import javax.annotation.PostConstruct;

/**
 * The type Mqtt publisher runner.
 */
public abstract class MqttPublisherRunner extends MqttPublisher {

    /**
     * Instantiates a new Mqtt publisher runner.
     *
     * @param applicationProperties the application properties
     * @throws Exception the exception
     */
    public MqttPublisherRunner(BrokerApplicationProperties applicationProperties) throws Exception {
        super(applicationProperties);
    }

    /**
     * start the Publisher runner routine
     */
    @PostConstruct
    protected abstract void start();
}
