package de.htw.saar.smartcity.virtualization.broker;


import de.htw.saar.smartcity.aggregator.lib.broker.Agent;
import de.htw.saar.smartcity.aggregator.lib.broker.MqttPublisher;
import de.htw.saar.smartcity.aggregator.lib.properties.BrokerApplicationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class MqttPublisherImpl extends MqttPublisher {

    private final String prefix = "data/aggregator";

    /**
     * Instantiates a new Mqtt subscriber.
     *
     * @param applicationProperties
     */
    public MqttPublisherImpl(BrokerApplicationProperties applicationProperties) {
        super(applicationProperties);
    }

    @Override
    protected void start() {
        {

            try {
                ArrayList<Agent> agents = new ArrayList<>();
                for(int i=1; i<=400; i++) {
                    agents.add(new TemperatureAgent(this, prefix + "/temperature" + "/sensor" + i));
                    //agents.add(new HumidityAgent(this, prefix + "/humidity" + "/sensor" + i));
                }
                agents.forEach(Agent::start);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
