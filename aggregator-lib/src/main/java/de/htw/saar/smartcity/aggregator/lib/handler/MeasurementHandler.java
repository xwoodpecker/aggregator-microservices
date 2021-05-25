package de.htw.saar.smartcity.aggregator.lib.handler;

import de.htw.saar.smartcity.aggregator.lib.broker.Producer;
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
    private final Producer producer;

    public MeasurementHandler(BaseMicroserviceApplicationProperties baseMicroserviceApplicationProperties,
                              SensorTypeService sensorTypeService,
                              MeasurementFactory measurementFactory,
                              StorageWrapper storageWrapper,
                              Producer producer) {

        this.baseMicroserviceApplicationProperties = baseMicroserviceApplicationProperties;
        this.sensorTypeService = sensorTypeService;
        this.measurementFactory = measurementFactory;
        this.storageWrapper = storageWrapper;
        this.producer = producer;
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

        Measurement m = measurementFactory.create(measurement);
        final Long sensorId = sensor.getId();
        String url = storageWrapper.putMeasurement(sensor.getName(), m);

        sensor.getGroups().forEach(
                g -> producer.publish(
                        String.format("%s.%s.%s", g.getGroupType().getName(), g.getId(), sensorId),
                        url
                )
        );

    }
}
