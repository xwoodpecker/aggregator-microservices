package de.htw.saar.smartcity.virtualization.broker;


import de.htw.saar.smartcity.aggregator.lib.broker.MqttPublisherRunner;
import de.htw.saar.smartcity.virtualization.api.OpenWeatherAPIWrapper;
import de.htw.saar.smartcity.virtualization.api.RealWeatherAgent;
import de.htw.saar.smartcity.virtualization.base.IAgent;
import de.htw.saar.smartcity.virtualization.properties.VirtualizationApplicationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Mqtt publisher.
 */
@Component
public class MqttPublisherRunnerImpl extends MqttPublisherRunner {


    private static final Logger log = LoggerFactory.getLogger(MqttPublisherRunnerImpl.class);

    private final static String PREFIX = "data/aggregator";
    private final static String TEMPERATURE_PREFIX = PREFIX + "/temperature";
    private final static String HUMIDITY_PREFIX = PREFIX + "/humidity";
    private final static String WATER_PREFIX = PREFIX + "/water";

    private final static Integer ITERATIONS = 10;
    private final static Integer INTERVAL = 30 * 1000;

    private static List<Integer> cities;

    /**
     * Instantiates a new Mqtt subscriber.
     *
     * @param applicationProperties the application properties
     * @throws Exception the exception
     */
    public MqttPublisherRunnerImpl(VirtualizationApplicationProperties applicationProperties) throws Exception {
        super(applicationProperties);
        OpenWeatherAPIWrapper.setApiKey(applicationProperties.getOpenWeatherAPIKey());
        cities = applicationProperties.getOpenWeatherAPICities().stream().map(Integer::parseInt).collect(Collectors.toList());
    }

    @Override
    protected void start() {

        try {
            ArrayList<IAgent> agents = new ArrayList<>();

            for(int i = 1; i <= cities.size(); i++) {
                String sensorSuffix = "/sensor" + i;
                agents.add(new RealWeatherAgent(this,
                        TEMPERATURE_PREFIX + sensorSuffix,
                        HUMIDITY_PREFIX + sensorSuffix,
                        WATER_PREFIX + sensorSuffix,
                        INTERVAL,
                        cities.get(i-1))
                );
            }

            agents.forEach(IAgent::start);

        } catch (Exception e) {
            log.error("Exception during agent execution!");
            //e.printStackTrace();
        }
    }
}

//old code kept for reference
/**
 for(int i=1; i<=ITERATIONS; i++) {
 //random values
 agents.add(new TemperatureAgent(this, PREFIX + "/temperature/sensor" + i, INTERVAL));
 agents.add(new HumidityAgent(this, PREFIX + "/humidity/sensor" + i, INTERVAL));
 agents.add(new PictureAgent(this, PREFIX + "/picture/sensor" + i, INTERVAL));
 }
 **/



/**
 //real values
 //2842647 - Saarbrücken
 String sensorSuffix = "/sensor1";
 agents.add(new RealWeatherAgent(this,
 TEMPERATURE_PREFIX + sensorSuffix,
 HUMIDITY_PREFIX + sensorSuffix,
 WATER_PREFIX + sensorSuffix,
 INTERVAL,
 2842647)
 );

 //524901 - Moscow
 sensorSuffix = "/sensor2";
 agents.add(new RealWeatherAgent(this,
 TEMPERATURE_PREFIX + sensorSuffix,
 HUMIDITY_PREFIX + sensorSuffix,
 WATER_PREFIX + sensorSuffix,
 INTERVAL,
 524901)
 );

 //6255152 - Antarctica
 sensorSuffix = "/sensor3";
 agents.add(new RealWeatherAgent(this,
 TEMPERATURE_PREFIX + sensorSuffix,
 HUMIDITY_PREFIX + sensorSuffix,
 WATER_PREFIX + sensorSuffix,
 INTERVAL,
 6255152)
 );

 //3169070 - Rome
 sensorSuffix = "/sensor4";
 agents.add(new RealWeatherAgent(this,
 TEMPERATURE_PREFIX + sensorSuffix,
 HUMIDITY_PREFIX + sensorSuffix,
 WATER_PREFIX + sensorSuffix,
 INTERVAL,
 3169070)
 );

 //5128581 - New York City
 sensorSuffix = "/sensor5";
 agents.add(new RealWeatherAgent(this,
 TEMPERATURE_PREFIX + sensorSuffix,
 HUMIDITY_PREFIX + sensorSuffix,
 WATER_PREFIX + sensorSuffix,
 INTERVAL,
 5128581)
 );

 //1850147 - Tokyo
 sensorSuffix = "/sensor6";
 agents.add(new RealWeatherAgent(this,
 TEMPERATURE_PREFIX + sensorSuffix,
 HUMIDITY_PREFIX + sensorSuffix,
 WATER_PREFIX + sensorSuffix,
 INTERVAL,
 1850147)
 );**/