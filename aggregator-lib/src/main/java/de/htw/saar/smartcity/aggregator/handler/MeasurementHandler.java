package de.htw.saar.smartcity.aggregator.handler;

import de.htw.saar.smartcity.aggregator.factory.MeasurementFactory;
import de.htw.saar.smartcity.aggregator.model.Measurement;
import de.htw.saar.smartcity.aggregator.model.SensorMeasurement;
import de.htw.saar.smartcity.aggregator.storage.StorageWrapper;
import de.htw.saar.smartcity.aggregator.entity.Sensor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class MeasurementHandler  {

    private static final Logger log = LoggerFactory.getLogger(MeasurementHandler.class);

    private final MeasurementFactory measurementFactory;
    private final StorageWrapper storageWrapper;

    public MeasurementHandler(MeasurementFactory measurementFactory, StorageWrapper storageWrapper) {

        this.measurementFactory = measurementFactory;
        this.storageWrapper = storageWrapper;
    }

    public void handleMessage(SensorMeasurement sensorMeasurement){

        String sensorName = sensorMeasurement.getSensorName();
        String measurement = sensorMeasurement.getMeasurement();
        log.info("Message arrived for sensor " + sensorName + " Measurement: " + measurement);

        Sensor sensor = storageWrapper.getSensor(sensorName);
        if(sensor == null) {
            sensor = new Sensor();
            sensor.setName(sensorName);
            storageWrapper.putSensor(sensor);
        }

       Measurement m = measurementFactory.create(sensor, measurement);
        storageWrapper.putMeasurement(m);

    }
}
