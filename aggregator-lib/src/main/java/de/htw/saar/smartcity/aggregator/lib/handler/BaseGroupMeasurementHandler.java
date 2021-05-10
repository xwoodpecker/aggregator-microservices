package de.htw.saar.smartcity.aggregator.lib.handler;

import de.htw.saar.smartcity.aggregator.lib.entity.Sensor;
import de.htw.saar.smartcity.aggregator.lib.factory.MeasurementFactory;
import de.htw.saar.smartcity.aggregator.lib.model.GroupCombinator;
import de.htw.saar.smartcity.aggregator.lib.model.GroupMeasurement;
import de.htw.saar.smartcity.aggregator.lib.model.Measurement;
import de.htw.saar.smartcity.aggregator.lib.model.SensorGroupMeasurement;
import de.htw.saar.smartcity.aggregator.lib.storage.StorageWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class BaseGroupMeasurementHandler implements GroupMeasurementHandler{

    private static final Logger log = LoggerFactory.getLogger(BaseGroupMeasurementHandler.class);

    private final MeasurementFactory measurementFactory;
    private final StorageWrapper storageWrapper;

    protected List<GroupCombinator> groupCombinators = new ArrayList<>();

    public BaseGroupMeasurementHandler(MeasurementFactory measurementFactory, StorageWrapper storageWrapper) {
        this.measurementFactory = measurementFactory;
        this.storageWrapper = storageWrapper;
        
        addCombinators();
    }

    protected abstract void addCombinators();

    public void handleSensorGroupMeasurement(SensorGroupMeasurement sensorGroupMeasurement){
        String groupName = sensorGroupMeasurement.getGroupName();
        log.info("Message arrived for group " + groupName );

        Map<String, String> measurements = sensorGroupMeasurement.getSensorMeasurements();

        for(GroupCombinator groupCombinator : groupCombinators) {
            GroupMeasurement groupMeasurement = new GroupMeasurement();

            groupMeasurement.setTime(LocalDateTime.now());
            groupMeasurement.setGroupName(groupName);

            for (String sensorName : measurements.keySet()) {
                String measurement = measurements.get(sensorName);
                Sensor sensor = storageWrapper.getSensor(sensorName);
                //todo: check

                Measurement m = measurementFactory.create(sensor, measurement);
                groupMeasurement.getSensorNameMeasurementMap().putIfAbsent(sensorName, m);
            }
            groupMeasurement.setCombinator(groupCombinator);
            groupMeasurement.combine();

            storageWrapper.putMeasurement(groupMeasurement);
        }
    }
}
