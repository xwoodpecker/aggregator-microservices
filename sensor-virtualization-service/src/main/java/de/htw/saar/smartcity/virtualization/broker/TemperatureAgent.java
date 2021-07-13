package de.htw.saar.smartcity.virtualization.broker;

import de.htw.saar.smartcity.aggregator.lib.broker.MqttPublisher;

import java.util.Random;

public class TemperatureAgent extends Agent {

    private Random rand = new Random();

    public TemperatureAgent(MqttPublisher publisher, String sensorName, Integer interval) {
        super(publisher, sensorName, interval);
    }

    @Override
    protected Double getNextValue() {
        return Double.valueOf(rand.nextInt(50) - 10);
    }
}
