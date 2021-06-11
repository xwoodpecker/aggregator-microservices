package de.htw.saar.smartcity.virtualization.broker;

import de.htw.saar.smartcity.aggregator.lib.broker.Agent;
import de.htw.saar.smartcity.aggregator.lib.broker.MqttPublisher;

import java.util.Random;

public class HumidityAgent extends Agent {

    private Random rand = new Random();

    /**
     * Instantiates a new Agent.
     *
     * @param publisher
     * @param sensorName the sensor name
     */
    public HumidityAgent(MqttPublisher publisher, String sensorName) {
        super(publisher, sensorName);
    }

    @Override
    protected Double getNextValue() {
        return Double.valueOf(rand.nextInt(100));
    }
}
