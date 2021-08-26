package de.htw.saar.smartcity.virtualization.temperature;

import de.htw.saar.smartcity.aggregator.lib.broker.MqttPublisherRunner;
import de.htw.saar.smartcity.virtualization.base.Agent;

import java.util.Random;

/**
 * The type Temperature agent.
 */
public class TemperatureAgent extends Agent {

    private Random rand = new Random();

    /**
     * Instantiates a new Temperature agent.
     *
     * @param publisher  the publisher
     * @param sensorName the sensor name
     * @param interval   the interval
     */
    public TemperatureAgent(MqttPublisherRunner publisher, String sensorName, Integer interval) {
        super(publisher, sensorName, interval);
    }

    @Override
    protected Double getNextValue() {
        return Math.round((rand.nextDouble() * 50 - 10) * 100) / 100.0;
    }
}
