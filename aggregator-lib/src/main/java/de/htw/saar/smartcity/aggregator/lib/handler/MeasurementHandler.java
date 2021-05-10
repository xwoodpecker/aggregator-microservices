package de.htw.saar.smartcity.aggregator.lib.handler;

import de.htw.saar.smartcity.aggregator.lib.entity.Sensor;
import de.htw.saar.smartcity.aggregator.lib.entity.SensorType;
import de.htw.saar.smartcity.aggregator.lib.factory.MeasurementFactory;
import de.htw.saar.smartcity.aggregator.lib.model.Measurement;
import de.htw.saar.smartcity.aggregator.lib.model.SensorMeasurement;
import de.htw.saar.smartcity.aggregator.lib.service.SensorTypeService;
import de.htw.saar.smartcity.aggregator.lib.storage.StorageWrapper;
import de.htw.saar.smartcity.aggregator.lib.properties.BaseMicroserviceApplicationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class MeasurementHandler  {

    private static final Logger log = LoggerFactory.getLogger(MeasurementHandler.class);

    private final BaseMicroserviceApplicationProperties baseMicroserviceApplicationProperties;
    private final SensorTypeService sensorTypeService;
    private final MeasurementFactory measurementFactory;
    private final StorageWrapper storageWrapper;

    public MeasurementHandler(BaseMicroserviceApplicationProperties baseMicroserviceApplicationProperties, SensorTypeService sensorTypeService, MeasurementFactory measurementFactory, StorageWrapper storageWrapper) {
        this.baseMicroserviceApplicationProperties = baseMicroserviceApplicationProperties;
        this.sensorTypeService = sensorTypeService;
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
            String sensorTypeName = baseMicroserviceApplicationProperties.getMicroServiceSensorType();
            SensorType sensorType = sensorTypeService.findSensorTypeByName(sensorTypeName);
            sensor.setSensorType(sensorType);
            storageWrapper.putSensor(sensor);
        }

       Measurement m = measurementFactory.create(sensor, measurement);
        storageWrapper.putMeasurement(m);

    }
}
