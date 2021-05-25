package de.htw.saar.smartcity.aggregator.lib.handler;

import de.htw.saar.smartcity.aggregator.lib.broker.Publisher;
import de.htw.saar.smartcity.aggregator.lib.entity.DataType;
import de.htw.saar.smartcity.aggregator.lib.entity.Sensor;
import de.htw.saar.smartcity.aggregator.lib.factory.MeasurementFactory;
import de.htw.saar.smartcity.aggregator.lib.model.Measurement;
import de.htw.saar.smartcity.aggregator.lib.model.SensorMeasurement;
import de.htw.saar.smartcity.aggregator.lib.service.DataTypeService;
import de.htw.saar.smartcity.aggregator.lib.storage.StorageWrapper;
import de.htw.saar.smartcity.aggregator.lib.properties.RawMicroserviceApplicationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class RawMeasurementHandler {

    private static final Logger log = LoggerFactory.getLogger(RawMeasurementHandler.class);

    private final RawMicroserviceApplicationProperties rawMicroserviceApplicationProperties;
    private final DataTypeService dataTypeService;
    private final MeasurementFactory measurementFactory;
    private final StorageWrapper storageWrapper;
    private final Publisher publisher;

    public RawMeasurementHandler(RawMicroserviceApplicationProperties rawMicroserviceApplicationProperties,
                                 DataTypeService dataTypeService,
                                 MeasurementFactory measurementFactory,
                                 StorageWrapper storageWrapper,
                                 Publisher publisher) {

        this.rawMicroserviceApplicationProperties = rawMicroserviceApplicationProperties;
        this.dataTypeService = dataTypeService;
        this.measurementFactory = measurementFactory;
        this.storageWrapper = storageWrapper;
        this.publisher = publisher;
    }

    public void handleMessage(SensorMeasurement sensorMeasurement){

        String sensorName = sensorMeasurement.getSensorName();
        String measurement = sensorMeasurement.getMeasurement();
        log.info("Message arrived for sensor " + sensorName + " Measurement: " + measurement);

        Sensor sensor = storageWrapper.getSensor(sensorName);

        if(sensor == null) {

            sensor = new Sensor();
            sensor.setName(sensorName);
            String sensorTypeName = rawMicroserviceApplicationProperties.getMicroServiceSensorType();
            DataType dataType = dataTypeService.findDataTypeByName(sensorTypeName);
            sensor.setDataType(dataType);
            storageWrapper.putSensor(sensor);
        }

        Measurement m = measurementFactory.create(measurement);
        final Long sensorId = sensor.getId();
        String url = storageWrapper.putMeasurement(sensor.getName(), m);

        sensor.getGroups().forEach(
                g -> publisher.publish(
                        String.format("%s.%s.%s", g.getGroupType().getName(), g.getId(), sensorId),
                        url
                )
        );

    }
}
