package de.htw.saar.smartcity.aggregator.lib.broker;

import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * The type Agent.
 */
public abstract class Agent {

    /**
     * The Publisher
     */
    private MqttPublisher publisher;

    /**
     * The Sensor name.
     */
    private String sensorName;
    /**
     * The Random.
     */
    private Random random;

    /**
     * Instantiates a new Agent.
     *
     * @param sensorName the sensor name
     */

    private Integer interval;

    public Agent(MqttPublisher publisher, String sensorName, Integer interval){
        this.publisher = publisher;
        this.sensorName = sensorName;
        this.interval =  interval;
        this.random = new Random();
    }

    /**
     * Start.
     */
    public void start() {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(() -> {
            try {
                publisher.publish(sensorName, String.valueOf(getNextValue()));

            } catch (MqttException e) {
                e.printStackTrace();
            }
        }, random.nextInt(1000), interval, TimeUnit.MILLISECONDS);
    }

    protected abstract Object getNextValue();
}
