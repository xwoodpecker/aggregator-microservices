package de.htw.saar.smartcity.aggregator.model;

import de.htw.saar.smartcity.aggregator.entity.Group;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class SensorGroupMeasurement implements Serializable {
    private Map<String, String> sensorMeasurements = new HashMap<>();
    private Integer maximumSize;
    private String groupName;

    public SensorGroupMeasurement() {

    }

    public SensorGroupMeasurement(Group group) {
        this.maximumSize = group.getAllSensorsRecursive().size();
        this.groupName = group.getName();
    }

    public Map<String, String> getSensorMeasurements() {
        return sensorMeasurements;
    }

    public String getGroupName() {
        return groupName;
    }

    public void addSensorMeasurement(SensorMeasurement sensorMeasurement) {
        sensorMeasurements.put(sensorMeasurement.getSensorName(), sensorMeasurement.getMeasurement());
    }

    public boolean allValuesSet() {
        return maximumSize == sensorMeasurements.size();
    }

    public void reset() {
        sensorMeasurements = new HashMap<>();
    }
}
