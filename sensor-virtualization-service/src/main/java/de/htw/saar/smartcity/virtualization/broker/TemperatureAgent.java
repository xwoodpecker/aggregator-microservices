package de.htw.saar.smartcity.virtualization.broker;

import de.htw.saar.smartcity.aggregator.lib.broker.MqttPublisher;

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
    public TemperatureAgent(MqttPublisher publisher, String sensorName, Integer interval) {
        super(publisher, sensorName, interval);
    }

    @Override
    protected Double getNextValue() {
        return Double.valueOf(rand.nextInt(50) - 10);
    }
}
