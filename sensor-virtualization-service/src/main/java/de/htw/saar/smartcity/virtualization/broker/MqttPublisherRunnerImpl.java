package de.htw.saar.smartcity.virtualization.broker;


import de.htw.saar.smartcity.aggregator.lib.broker.MqttPublisherRunner;
import de.htw.saar.smartcity.virtualization.airquality.AirqualityAgent;
import de.htw.saar.smartcity.virtualization.api.OpenWeatherAPIWrapper;
import de.htw.saar.smartcity.virtualization.api.RealWeatherAgent;
import de.htw.saar.smartcity.virtualization.base.IAgent;
import de.htw.saar.smartcity.virtualization.humidity.HumidityAgent;
import de.htw.saar.smartcity.virtualization.picture.PictureAgent;
import de.htw.saar.smartcity.virtualization.properties.VirtualizationApplicationProperties;
import de.htw.saar.smartcity.virtualization.temperature.TemperatureAgent;
import de.htw.saar.smartcity.virtualization.water.WaterAgent;
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


    private final VirtualizationApplicationProperties virtualizationApplicationProperties;

    private static final Logger log = LoggerFactory.getLogger(MqttPublisherRunnerImpl.class);

    private final static String PREFIX = "data/aggregator";
    private final static String TEMPERATURE_PREFIX = PREFIX + "/temperature";
    private final static String HUMIDITY_PREFIX = PREFIX + "/humidity";
    private final static String WATER_PREFIX = PREFIX + "/water";
    private final static String AIRQUALITY_PREFIX = PREFIX + "/airquality";
    private final static String PICTURE_PREFIX = PREFIX + "/picture";
    
    private static List<Integer> cities;
    private static Integer interval;

    /**
     * Instantiates a new Mqtt subscriber.
     *
     *
     * @param virtualizationApplicationProperties the application properties
     * @throws Exception the exception
     */
    public MqttPublisherRunnerImpl(VirtualizationApplicationProperties virtualizationApplicationProperties) throws Exception {

        super(virtualizationApplicationProperties);

        this.virtualizationApplicationProperties = virtualizationApplicationProperties;
        OpenWeatherAPIWrapper.setApiKey(virtualizationApplicationProperties.getOpenWeatherAPIKey());
        cities = virtualizationApplicationProperties.getOpenWeatherAPICities().stream().map(Integer::parseInt).collect(Collectors.toList());
        interval = 1000 * virtualizationApplicationProperties.getInterval();
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
                        interval,
                        cities.get(i-1))
                );
            }

            for(int i = 1 ; i <= virtualizationApplicationProperties.getTemperatureAgentCount(); i++) {
                String sensorSuffix = "/sensor" + i;
                agents.add(new TemperatureAgent(this,
                        TEMPERATURE_PREFIX + sensorSuffix,
                        interval)
                );
            }

            for(int i = 1 ; i <= virtualizationApplicationProperties.getHumidityAgentCount(); i++) {
                String sensorSuffix = "/sensor" + i;
                agents.add(new HumidityAgent(this,
                        HUMIDITY_PREFIX + sensorSuffix,
                        interval)
                );
            }

            for(int i = 1 ; i <= virtualizationApplicationProperties.getWaterAgentCount(); i++) {
                String sensorSuffix = "/sensor" + i;
                agents.add(new WaterAgent(this,
                        WATER_PREFIX + sensorSuffix,
                        interval)
                );
            }

            for(int i = 1 ; i <= virtualizationApplicationProperties.getPictureAgentCount(); i++) {
                String sensorSuffix = "/sensor" + i;
                agents.add(new AirqualityAgent(this,
                        AIRQUALITY_PREFIX + sensorSuffix,
                        interval)
                );
            }

            for(int i = 1 ; i <= virtualizationApplicationProperties.getPictureAgentCount(); i++) {
                String sensorSuffix = "/sensor" + i;
                agents.add(new PictureAgent(this,
                        PICTURE_PREFIX + sensorSuffix,
                        interval)
                );
            }

            agents.forEach(IAgent::start);

        } catch (Exception e) {
            log.error("Exception during agent execution!");
            //e.printStackTrace();
        }
    }
}

