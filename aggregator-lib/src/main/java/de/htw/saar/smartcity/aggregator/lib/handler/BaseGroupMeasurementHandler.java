package de.htw.saar.smartcity.aggregator.lib.handler;

import de.htw.saar.smartcity.aggregator.lib.entity.Sensor;
import de.htw.saar.smartcity.aggregator.lib.factory.MeasurementFactory;
import de.htw.saar.smartcity.aggregator.lib.model.BaseGroupCombinator;
import de.htw.saar.smartcity.aggregator.lib.model.BaseGroupMeasurement;
import de.htw.saar.smartcity.aggregator.lib.model.Measurement;
import de.htw.saar.smartcity.aggregator.lib.model.SensorGroupMeasurement;
import de.htw.saar.smartcity.aggregator.lib.storage.StorageWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class BaseGroupMeasurementHandler implements GroupMeasurementHandler{

    private static final Logger log = LoggerFactory.getLogger(BaseGroupMeasurementHandler.class);

    private final MeasurementFactory measurementFactory;
    private final StorageWrapper storageWrapper;

    protected List<BaseGroupCombinator> baseGroupCombinators = new ArrayList<>();

    public BaseGroupMeasurementHandler(MeasurementFactory measurementFactory, StorageWrapper storageWrapper) {
        this.measurementFactory = measurementFactory;
        this.storageWrapper = storageWrapper;

    }

    @PostConstruct
    protected abstract void addCombinators();

    public void handleSensorGroupMeasurement(SensorGroupMeasurement sensorGroupMeasurement){
        String groupName = sensorGroupMeasurement.getGroupName();
        log.info("Message arrived for group " + groupName );

        Map<String, String> measurements = sensorGroupMeasurement.getSensorMeasurements();


        BaseGroupMeasurement baseGroupMeasurement = new BaseGroupMeasurement();
        baseGroupMeasurement.setTime(LocalDateTime.now());
        baseGroupMeasurement.setGroupName(groupName);

        for (String sensorName : measurements.keySet()) {

            String measurement = measurements.get(sensorName);
            Sensor sensor = storageWrapper.getSensor(sensorName);
            //todo: check

            Measurement m = measurementFactory.create(sensor, measurement);
            baseGroupMeasurement.getSensorNameMeasurementMap().putIfAbsent(sensorName, m);
        }

        for(BaseGroupCombinator baseGroupCombinator : baseGroupCombinators) {

            baseGroupMeasurement.setGroupCombinator(baseGroupCombinator);
            baseGroupMeasurement.combine();

            storageWrapper.putMeasurement(baseGroupMeasurement);
        }
    }
}
