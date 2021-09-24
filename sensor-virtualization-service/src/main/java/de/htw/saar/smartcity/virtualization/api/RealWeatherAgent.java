package de.htw.saar.smartcity.virtualization.api;

import de.htw.saar.smartcity.aggregator.lib.broker.MqttPublisherRunner;
import de.htw.saar.smartcity.virtualization.base.IAgent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * The type Real temperature humidity agent.
 */
public class RealWeatherAgent implements IAgent {

    private static final Logger log = LoggerFactory.getLogger(RealWeatherAgent.class);

    /**
     * The Publisher
     */
    private MqttPublisherRunner publisher;

    /**
     * The Temperature sensor name.
     */
    private final String temperatureSensorName;

    /**
     * The Humidity sensor name.
     */
    private final String humiditySensorName;


    /**
     * The Water sensor name.
     */
    private final String waterSensorName;

    /**
     * The Random.
     */
    private Random random;


    /**
     * The Interval.
     */
    private Integer interval;


    /**
     * The CityId.
     */
    private final Integer cityId;


    /**
     * Instantiates a new Real temperature humidity agent.
     *
     * @param publisher             the publisher
     * @param temperatureSensorName the temperature sensor name
     * @param humiditySensorName    the humidity sensor name
     * @param waterSensorName       the water sensor name
     * @param interval              the interval
     * @param cityId                the city id
     */
    public RealWeatherAgent(MqttPublisherRunner publisher,
                            String temperatureSensorName,
                            String humiditySensorName,
                            String waterSensorName,
                            Integer interval,
                            Integer cityId) {

        this.publisher = publisher;
        this.temperatureSensorName = temperatureSensorName;
        this.humiditySensorName = humiditySensorName;
        this.waterSensorName = waterSensorName;
        this.interval =  interval;
        this.cityId = cityId;
        this.random = new Random();
    }

    /**
     * Start.
     */
    public void start() {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(() -> {
            try {
                WeatherData weatherData = OpenWeatherAPIWrapper.getTemperatureHumidity(cityId);
                publisher.publish(temperatureSensorName, String.valueOf(weatherData.getTemperature()));
                Thread.sleep(random.nextInt(2000));
                publisher.publish(humiditySensorName, String.valueOf(weatherData.getHumidity()));
                Thread.sleep(random.nextInt(2000));
                publisher.publish(waterSensorName, String.valueOf(weatherData.getRainy()));
            } catch (Throwable t) {
                log.error("Exception during publish of RealWeather.");
                //e.printStackTrace();
            }
        }, random.nextInt(interval), interval, TimeUnit.MILLISECONDS);
    }
}
