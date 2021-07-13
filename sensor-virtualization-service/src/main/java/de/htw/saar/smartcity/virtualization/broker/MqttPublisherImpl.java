package de.htw.saar.smartcity.virtualization.broker;


import de.htw.saar.smartcity.aggregator.lib.broker.MqttPublisher;
import de.htw.saar.smartcity.virtualization.properties.BrokerApplicationPropertiesImpl;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class MqttPublisherImpl extends MqttPublisher {

    private final static String PREFIX = "data/aggregator";
    private final static String TEMPERATURE_PREFIX = PREFIX + "/temperature";
    private final static String HUMIDITY_PREFIX = PREFIX + "/humidity";

    private final static Integer ITERATIONS = 10;
    private final static Integer INTERVAL = 30 * 1000;

    /**
     * Instantiates a new Mqtt subscriber.
     *
     * @param applicationProperties
     */
    public MqttPublisherImpl(BrokerApplicationPropertiesImpl applicationProperties) throws Exception {
        super(applicationProperties);
    }

    @Override
    protected void start() {
        {

            try {
                ArrayList<IAgent> agents = new ArrayList<>();
                /**
                for(int i=1; i<=ITERATIONS; i++) {
                    //random values
                    agents.add(new TemperatureAgent(this, PREFIX + "/temperature/sensor" + i, INTERVAL));
                    agents.add(new HumidityAgent(this, PREFIX + "/humidity/sensor" + i, INTERVAL));
                    agents.add(new PictureAgent(this, PREFIX + "/picture/sensor" + i, INTERVAL));
                }
                **/

                //real values
                //2842647 - Saarbrücken
                agents.add(new RealTemperatureHumidityAgent(this,
                        TEMPERATURE_PREFIX + "/sensor1",
                        HUMIDITY_PREFIX + "/sensor1",
                        INTERVAL,
                        2842647)
                );

                //524901 - Moscow
                agents.add(new RealTemperatureHumidityAgent(this,
                        TEMPERATURE_PREFIX + "/sensor2",
                        HUMIDITY_PREFIX + "/sensor2",
                        INTERVAL,
                        524901)
                );

                //6255152 - Antarctica
                agents.add(new RealTemperatureHumidityAgent(this,
                        TEMPERATURE_PREFIX + "/sensor3",
                        HUMIDITY_PREFIX + "/sensor3",
                        INTERVAL,
                        6255152)
                );

                //3169070 - Rome
                agents.add(new RealTemperatureHumidityAgent(this,
                        TEMPERATURE_PREFIX + "/sensor4",
                        HUMIDITY_PREFIX + "/sensor4",
                        INTERVAL,
                        3169070)
                );

                //5128581 - New York City
                agents.add(new RealTemperatureHumidityAgent(this,
                        TEMPERATURE_PREFIX + "/sensor5",
                        HUMIDITY_PREFIX + "/sensor5",
                        INTERVAL,
                        5128581)
                );

                //1850147 - Tokyo
                agents.add(new RealTemperatureHumidityAgent(this,
                        TEMPERATURE_PREFIX + "/sensor6",
                        HUMIDITY_PREFIX + "/sensor6",
                        INTERVAL,
                        1850147)
                );

                agents.forEach(IAgent::start);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
