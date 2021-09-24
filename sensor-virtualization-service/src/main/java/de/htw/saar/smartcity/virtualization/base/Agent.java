package de.htw.saar.smartcity.virtualization.base;

import de.htw.saar.smartcity.aggregator.lib.broker.MqttPublisherRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * The type Agent.
 */
public abstract class Agent implements IAgent {


    private static final Logger log = LoggerFactory.getLogger(Agent.class);

    /**
     * The Publisher
     */
    private MqttPublisherRunner publisher;

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

    /**
     * Instantiates a new Agent.
     *
     * @param publisher  the publisher
     * @param sensorName the sensor name
     * @param interval   the interval
     */
    public Agent(MqttPublisherRunner publisher, String sensorName, Integer interval){
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
            } catch (Throwable t) {
                log.error("Exception during publish.");
                //e.printStackTrace();
            }
        }, random.nextInt(interval), interval, TimeUnit.MILLISECONDS);
    }

    /**
     * Gets next value.
     *
     * @return the next value
     */
    protected abstract Object getNextValue();
}
