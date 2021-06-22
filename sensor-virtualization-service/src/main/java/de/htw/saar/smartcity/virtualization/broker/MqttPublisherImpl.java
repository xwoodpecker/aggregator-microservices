package de.htw.saar.smartcity.virtualization.broker;


import de.htw.saar.smartcity.aggregator.lib.broker.Agent;
import de.htw.saar.smartcity.aggregator.lib.broker.MqttPublisher;
import de.htw.saar.smartcity.virtualization.properties.BrokerApplicationPropertiesImpl;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class MqttPublisherImpl extends MqttPublisher {

    private final static String PREFIX = "data/aggregator";

    private final static Integer ITERATIONS = 1000;

    /**
     * Instantiates a new Mqtt subscriber.
     *
     * @param applicationProperties
     */
    public MqttPublisherImpl(BrokerApplicationPropertiesImpl applicationProperties) {
        super(applicationProperties);
    }

    @Override
    protected void start() {
        {

            try {
                ArrayList<Agent> agents = new ArrayList<>();
                for(int i=1; i<=ITERATIONS; i++) {
                    agents.add(new TemperatureAgent(this, PREFIX + "/temperature/sensor" + i, 60*1000));
                    //agents.add(new HumidityAgent(this, PREFIX + "/humidity/sensor" + i, 30*1000));
                    //agents.add(new PictureAgent(this, PREFIX + "/picture/sensor" + i, 30*1000));
                }
                agents.forEach(Agent::start);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
