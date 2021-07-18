package de.htw.saar.smartcity.virtualization.broker;

import de.htw.saar.smartcity.aggregator.lib.broker.MqttPublisher;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * The type Real temperature humidity agent.
 */
public class RealTemperatureHumidityAgent implements IAgent{

    private static final Logger log = LoggerFactory.getLogger(RealTemperatureHumidityAgent.class);

    /**
     * The Publisher
     */
    private MqttPublisher publisher;

    /**
     * The Temperature sensor name.
     */
    private String temperatureSensorName;

    /**
     * The Humidity sensor name.
     */
    private String humiditySensorName;

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
     * @param interval              the interval
     * @param cityId                the city id
     */
    public RealTemperatureHumidityAgent(MqttPublisher publisher, String temperatureSensorName, String humiditySensorName, Integer interval, Integer cityId) {
        this.publisher = publisher;
        this.temperatureSensorName = temperatureSensorName;
        this.humiditySensorName = humiditySensorName;
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
                TemperatureHumidity temperatureHumidity = OpenWeatherAPIWrapper.getTemperatureHumidity(cityId);
                publisher.publish(temperatureSensorName, String.valueOf(temperatureHumidity.getTemperature()));
                Thread.sleep(1000);
                publisher.publish(humiditySensorName, String.valueOf(temperatureHumidity.getHumidity()));
            } catch (Exception e) {
                log.error("Exception during publish.");
                e.printStackTrace();
            }
        }, random.nextInt(interval), interval, TimeUnit.MILLISECONDS);
    }
}
