package de.htw.saar.smartcity.aggregator.lib.handler;

import de.htw.saar.smartcity.aggregator.lib.entity.SensorType;
import de.htw.saar.smartcity.aggregator.lib.entity.Sensor;
import de.htw.saar.smartcity.aggregator.lib.factory.MeasurementFactory;
import de.htw.saar.smartcity.aggregator.lib.model.Measurement;
import de.htw.saar.smartcity.aggregator.lib.model.MixedGroupCombinator;
import de.htw.saar.smartcity.aggregator.lib.model.MixedGroupMeasurement;
import de.htw.saar.smartcity.aggregator.lib.model.SensorGroupMeasurement;
import de.htw.saar.smartcity.aggregator.lib.storage.StorageWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class MixedGroupMeasurementHandler implements GroupMeasurementHandler{

    private static final Logger log = LoggerFactory.getLogger(MixedGroupMeasurementHandler.class);

    private final StorageWrapper storageWrapper;

    protected Map<Long, MeasurementFactory> sensorTypeIdMeasurementFactoryMap = new HashMap<>();
    protected List<MixedGroupCombinator> mixedGroupCombinators = new ArrayList<>();

    public MixedGroupMeasurementHandler(StorageWrapper storageWrapper) {
        this.storageWrapper = storageWrapper;

    }

    @PostConstruct
    private void init() {
        addMeasurementFactories();
        addCombinators();
    }

    protected abstract void addMeasurementFactories();

    protected abstract void addCombinators();


    public void handleSensorGroupMeasurement(SensorGroupMeasurement sensorGroupMeasurement){
        String groupName = sensorGroupMeasurement.getGroupName();
        log.info("Message arrived for group " + groupName );

        Map<String, String> measurements = sensorGroupMeasurement.getSensorMeasurements();

        MixedGroupMeasurement mixedGroupMeasurement = new MixedGroupMeasurement();

        mixedGroupMeasurement.setTime(LocalDateTime.now());
        mixedGroupMeasurement.setGroupName(groupName);

        for (String sensorName : measurements.keySet()) {
            String measurement = measurements.get(sensorName);
            Sensor sensor = storageWrapper.getSensor(sensorName);
            SensorType sensorType = sensor.getSensorType();
            Long sensorTypeId = sensorType.getId();

            Measurement m = sensorTypeIdMeasurementFactoryMap.get(sensorTypeId).create(sensor, measurement);
            mixedGroupMeasurement.getSensorTypeMeasurementMap().putIfAbsent(sensorType.getId(), m);
        }

        for(MixedGroupCombinator mixedGroupCombinator : mixedGroupCombinators) {

            mixedGroupMeasurement.setGroupCombinator(mixedGroupCombinator);
            mixedGroupMeasurement.combine();

            storageWrapper.putMeasurement(mixedGroupMeasurement);
        }
    }
}
