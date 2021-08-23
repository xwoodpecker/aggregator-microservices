package de.htw.saar.smartcity.sensor.app.temperatureandhumidity;

import de.htw.saar.smartcity.aggregator.lib.utils.Utils;
import de.htw.saar.smartcity.sensor.app.base.Monitor;
import de.htw.saar.smartcity.sensor.app.broker.MqttPublisherImpl;
import de.htw.saar.smartcity.sensor.app.base.SensorAgent;
import org.iot.raspberry.grovepi.GrovePi;
import org.iot.raspberry.grovepi.devices.GroveTemperatureAndHumiditySensor;
import org.iot.raspberry.grovepi.devices.GroveTemperatureAndHumidityValue;

import java.io.IOException;

/**
 * The type Temperature and humidity sensor.
 */
public class TemperatureAndHumiditySensor implements SensorAgent {

    private int pin;
    private int interval;
    private String temperatureTopic;
    private String humidityTopic;

    /**
     * Instantiates a new Temperature and humidity sensor.
     *
     * @param pin              the pin
     * @param interval         the interval
     * @param temperatureTopic the temperature topic
     * @param humidityTopic    the humidity topic
     */
    public TemperatureAndHumiditySensor(int pin, int interval, String temperatureTopic, String humidityTopic) {
        this.pin = pin;
        this.interval = interval;
        this.temperatureTopic = temperatureTopic;
        this.humidityTopic = humidityTopic;
    }

    @Override
    public void run(GrovePi grovePi, Monitor monitor, MqttPublisherImpl publisher) throws Exception {
        GroveTemperatureAndHumiditySensor dht = new GroveTemperatureAndHumiditySensor(grovePi, pin, GroveTemperatureAndHumiditySensor.Type.DHT11);
        while (monitor.isRunning()) {
            try {
                GroveTemperatureAndHumidityValue groveTemperatureAndHumidityValue = dht.get();
                if(!Utils.isBlankOrNull(humidityTopic)) {
                    double humidity = groveTemperatureAndHumidityValue.getHumidity();
                    System.out.println(humidityTopic + " : " + humidity);
                    publisher.publish(humidityTopic, String.valueOf(humidity));
                }
                if(!Utils.isBlankOrNull(temperatureTopic)) {
                    double temperature = groveTemperatureAndHumidityValue.getTemperature();
                    System.out.println(temperatureTopic + " : " + temperature);
                    publisher.publish(temperatureTopic, String.valueOf(temperature));
                }
                Thread.sleep(interval);
            } catch (IOException ex) {
            }
        }
    }
}