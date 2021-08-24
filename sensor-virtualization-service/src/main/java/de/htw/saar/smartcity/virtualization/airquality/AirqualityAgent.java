package de.htw.saar.smartcity.virtualization.airquality;

import de.htw.saar.smartcity.aggregator.lib.broker.MqttPublisherRunner;
import de.htw.saar.smartcity.virtualization.base.Agent;

import java.util.Random;

/**
 * The type Airquality agent.
 */
public class AirqualityAgent extends Agent {

    private Random rand = new Random();

    /**
     * Instantiates a new Airquality agent.
     *
     * @param publisher  the publisher
     * @param sensorName the sensor name
     * @param interval   the interval
     */
    public AirqualityAgent(MqttPublisherRunner publisher, String sensorName, Integer interval) {
        super(publisher, sensorName, interval);
    }

    @Override
    protected Double getNextValue() {
        return Double.valueOf(rand.nextInt(1000));
    }
}
