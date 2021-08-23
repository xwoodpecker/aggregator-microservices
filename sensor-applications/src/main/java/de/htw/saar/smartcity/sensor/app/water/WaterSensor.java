package de.htw.saar.smartcity.sensor.app.water;

import de.htw.saar.smartcity.sensor.app.base.Monitor;
import de.htw.saar.smartcity.sensor.app.base.SensorAgent;
import de.htw.saar.smartcity.sensor.app.broker.MqttPublisherImpl;
import org.iot.raspberry.grovepi.GrovePi;

import java.io.IOException;


/**
 * The type Water sensor.
 */
public class WaterSensor implements SensorAgent {

    private int pin;
    private int interval;
    private String topic;

    /**
     * Instantiates a new Water sensor.
     *
     * @param pin      the pin
     * @param interval the interval
     * @param topic    the topic
     */
    public WaterSensor(int pin, int interval, String topic) {
        this.pin = pin;
        this.interval = interval;
        this.topic = topic;
    }

    @Override
    public void run(GrovePi grovePi, Monitor monitor, MqttPublisherImpl publisher) throws Exception {
        WaterInputDevice w = new WaterInputDevice(grovePi, pin);
        while (monitor.isRunning()) {
            try {
                boolean water = w.get();
                System.out.println(topic + " : " + water);
                publisher.publish(topic, String.valueOf(water));
                Thread.sleep(interval);
            } catch (IOException ex) {
            }
        }
    }
}