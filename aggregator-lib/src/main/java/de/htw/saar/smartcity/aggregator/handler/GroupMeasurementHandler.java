package de.htw.saar.smartcity.aggregator.handler;

import de.htw.saar.smartcity.aggregator.entity.Sensor;
import de.htw.saar.smartcity.aggregator.factory.MeasurementFactory;
import de.htw.saar.smartcity.aggregator.model.Combinator;
import de.htw.saar.smartcity.aggregator.model.GroupMeasurement;
import de.htw.saar.smartcity.aggregator.model.Measurement;
import de.htw.saar.smartcity.aggregator.model.SensorGroupMeasurement;
import de.htw.saar.smartcity.aggregator.storage.StorageWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class GroupMeasurementHandler {

    private static final Logger log = LoggerFactory.getLogger(GroupMeasurementHandler.class);

    private final MeasurementFactory measurementFactory;
    private final StorageWrapper storageWrapper;

    protected List<Combinator> combinators = new ArrayList<>();

    public GroupMeasurementHandler(MeasurementFactory measurementFactory, StorageWrapper storageWrapper) {
        this.measurementFactory = measurementFactory;
        this.storageWrapper = storageWrapper;
        
        addCombinators();
    }

    protected abstract void addCombinators();

    public void handleSensorGroupMeasurement(SensorGroupMeasurement sensorGroupMeasurement){
        String groupName = sensorGroupMeasurement.getGroupName();
        log.info("Message arrived for group " + groupName );

        Map<String, String> measurements = sensorGroupMeasurement.getSensorMeasurements();

        for(Combinator combinator : combinators) {
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
            groupMeasurement.setCombinator(combinator);
            groupMeasurement.combine();

            storageWrapper.putMeasurement(groupMeasurement);
        }
    }
}
