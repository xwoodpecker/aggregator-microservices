package de.htw.saar.smartcity.virtualization.water;

import de.htw.saar.smartcity.aggregator.lib.broker.MqttPublisherRunner;
import de.htw.saar.smartcity.virtualization.base.Agent;

import java.util.Random;

/**
 * The type Water agent.
 */
public class WaterAgent extends Agent {

    private Random rand = new Random();

    /**
     * Instantiates a new Water agent.
     *
     * @param publisher  the publisher
     * @param sensorName the sensor name
     * @param interval   the interval
     */
    public WaterAgent(MqttPublisherRunner publisher, String sensorName, Integer interval) {
        super(publisher, sensorName, interval);
    }

    @Override
    protected Boolean getNextValue() {
        return rand.nextBoolean();
    }
}
