package de.htw.saar.smartcity.sensor.app.airquality;

import de.htw.saar.smartcity.sensor.app.base.Monitor;
import de.htw.saar.smartcity.sensor.app.base.SensorAgent;
import de.htw.saar.smartcity.sensor.app.broker.MqttPublisherImpl;
import org.iot.raspberry.grovepi.GrovePi;

import java.io.IOException;

/**
 * The type Air quality sensor.
 */
public class AirQualitySensor implements SensorAgent {

    private int pin;
    private int interval;
    private String topic;

    /**
     * Instantiates a new Air quality sensor.
     *
     * @param pin      the pin
     * @param interval the interval
     * @param topic    the topic
     */
    public AirQualitySensor(int pin , int interval, String topic){
        this.pin = pin;
        this.interval = interval;
        this.topic = topic;
    }

    @Override
    public void run(GrovePi grovePi, Monitor monitor, MqttPublisherImpl publisher) throws Exception {

        AirQualityInputDevice airQualityInputDevice = new AirQualityInputDevice(grovePi, pin);
        while (monitor.isRunning()) {
            try {
                Integer airQuality = airQualityInputDevice.get();
                System.out.println(topic + " : " + airQuality);
                publisher.publish(topic, String.valueOf(airQuality));
                Thread.sleep(interval);
            } catch (IOException ex) {
            }
        }
    }

}