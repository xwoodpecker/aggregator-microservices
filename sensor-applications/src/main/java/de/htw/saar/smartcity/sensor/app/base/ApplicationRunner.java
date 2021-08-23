package de.htw.saar.smartcity.sensor.app.base;

import de.htw.saar.smartcity.aggregator.lib.utils.Utils;
import de.htw.saar.smartcity.sensor.app.airquality.AirQualitySensor;
import de.htw.saar.smartcity.sensor.app.broker.MqttPublisherImpl;
import de.htw.saar.smartcity.sensor.app.properties.SensorApplicationProperties;
import de.htw.saar.smartcity.sensor.app.temperatureandhumidity.TemperatureAndHumiditySensor;
import de.htw.saar.smartcity.sensor.app.water.WaterSensor;
import org.iot.raspberry.grovepi.GrovePi;
import org.iot.raspberry.grovepi.pi4j.GrovePi4J;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The type Application runner.
 */
@Component
public class ApplicationRunner {

    private final SensorApplicationProperties sensorApplicationProperties;

    private final MqttPublisherImpl mqttPublisherImpl;

    private final ApplicationContext context;

    private final Semaphore lock = new Semaphore(0);


    /**
     * Instantiates a new Application runner.
     *
     * @param sensorApplicationProperties
     * @param mqttPublisherImpl the mqtt publisher
     * @param context           the context
     */
    public ApplicationRunner(SensorApplicationProperties sensorApplicationProperties, MqttPublisherImpl mqttPublisherImpl, ApplicationContext context) {
        this.sensorApplicationProperties = sensorApplicationProperties;
        this.mqttPublisherImpl = mqttPublisherImpl;
        this.context = context;

        try {
            this.start();
        } catch (Exception ex) {
            Logger.getLogger(ApplicationRunner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void start() throws IOException, InterruptedException {

        Logger.getLogger("GrovePi").setLevel(Level.WARNING);
        Logger.getLogger("RaspberryPi").setLevel(Level.WARNING);

        // control : start application once
        File control = new File("RUNNING_SENSORS");
        control.deleteOnExit();
        if (control.exists()) {
            // control : stop already running application
            control.delete();
            System.out.println("Stopping execution of already running application ");
            System.exit(SpringApplication.exit(context));
        }

        // control : create file for current application
        control.createNewFile();

        GrovePi grovePi = new GrovePi4J();


        System.out.println("RUNNING SENSORS - USING: PI4J");
        final ExecutorService runner = Executors.newCachedThreadPool();
        final ExecutorService fileMonitor = Executors.newSingleThreadExecutor();
        final Monitor monitor = new Monitor();

        List<SensorAgent> sensorAgentList = new ArrayList<>();
        if(!Utils.isBlankOrNull(sensorApplicationProperties.getAirqualitySensorName())) {
            AirQualitySensor airQualitySensor = new AirQualitySensor(
                    sensorApplicationProperties.getAirqualitySensorPin(),
                    sensorApplicationProperties.getInterval() * 1000,
                    sensorApplicationProperties.getAirqualitySensorName()
            );
            sensorAgentList.add(airQualitySensor);
        }

        if(!Utils.isBlankOrNull(sensorApplicationProperties.getTemperatureSensorName()) ||
                !Utils.isBlankOrNull(sensorApplicationProperties.getHumiditySensorName())) {
            TemperatureAndHumiditySensor temperatureAndHumiditySensor = new TemperatureAndHumiditySensor(
                    sensorApplicationProperties.getTemperatureAndHumiditySensorPin(),
                    sensorApplicationProperties.getInterval() * 1000,
                    sensorApplicationProperties.getTemperatureSensorName(),
                    sensorApplicationProperties.getHumiditySensorName()
            );
            sensorAgentList.add(temperatureAndHumiditySensor);
        }

        if(!Utils.isBlankOrNull(sensorApplicationProperties.getWaterSensorName())) {
            WaterSensor waterSensor = new WaterSensor(
                    sensorApplicationProperties.getWaterSensorPin(),
                    sensorApplicationProperties.getInterval() * 1000,
                    sensorApplicationProperties.getWaterSensorName()
            );
            sensorAgentList.add(waterSensor);
        }

        sensorAgentList.forEach(
            s ->  runner.execute(() -> {
                try {
                    s.run(grovePi, monitor, mqttPublisherImpl);
                } catch (Exception ex) {
                    Logger.getLogger(ApplicationRunner.class.getName()).log(Level.SEVERE, null, ex);
                }
                lock.release();
            })
        );

        fileMonitor.execute(() -> {
            while (control.exists()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                }
            }
            monitor.stop();
            lock.release();
        });


        lock.acquire();
        runner.shutdown();
        fileMonitor.shutdownNow();
        runner.awaitTermination(10, TimeUnit.SECONDS);
        control.delete();
        System.exit(SpringApplication.exit(context));
    }
}
