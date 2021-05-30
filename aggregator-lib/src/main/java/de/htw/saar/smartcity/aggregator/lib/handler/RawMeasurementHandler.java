package de.htw.saar.smartcity.aggregator.lib.handler;

import de.htw.saar.smartcity.aggregator.lib.broker.Publisher;
import de.htw.saar.smartcity.aggregator.lib.entity.DataType;
import de.htw.saar.smartcity.aggregator.lib.entity.Group;
import de.htw.saar.smartcity.aggregator.lib.entity.Sensor;
import de.htw.saar.smartcity.aggregator.lib.exception.MeasurementException;
import de.htw.saar.smartcity.aggregator.lib.factory.MeasurementFactory;
import de.htw.saar.smartcity.aggregator.lib.model.Measurement;
import de.htw.saar.smartcity.aggregator.lib.model.SensorMeasurement;
import de.htw.saar.smartcity.aggregator.lib.service.DataTypeService;
import de.htw.saar.smartcity.aggregator.lib.storage.StorageWrapper;
import de.htw.saar.smartcity.aggregator.lib.properties.RawMicroserviceApplicationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

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

        final String sensorName = sensorMeasurement.getSensorName();
        final String measurement = sensorMeasurement.getMeasurement();
        log.info("Message arrived for sensor " + sensorName + " Measurement: " + measurement);

        Sensor sensor = storageWrapper.getSensor(sensorName);

        if(sensor == null) {

            sensor = new Sensor();
            sensor.setName(sensorName);
            String dataTypeName = rawMicroserviceApplicationProperties.getMicroServiceDataType();
            DataType dataType = dataTypeService.findDataTypeByName(dataTypeName);
            sensor.setDataType(dataType);
            storageWrapper.putSensor(sensor);
            log.info("Sensor saved");
        }

        Measurement m;
        try {
             m = measurementFactory.create(measurement);

        } catch (MeasurementException me) {

            log.error("Measurement was malformed. Processing aborted.");
            return;
        }

        final Long sensorId = sensor.getId();
        final String objName = storageWrapper.putMeasurement(sensor.getName(), m);

        if(objName != null) {

            final String url = storageWrapper.getPresignedObjectUrl(objName);

            List<Group> activeGroups = sensor.getGroups().stream().filter(g -> g.getActive()).collect(Collectors.toList());
            if (activeGroups.size() > 0) {
                activeGroups.forEach(
                        g -> publisher.publish(
                                String.format("%s.%s.%s", g.getGroupType().getName(), g.getId(), sensorId),
                                url
                        )
                );
            }
        }

    }
}
