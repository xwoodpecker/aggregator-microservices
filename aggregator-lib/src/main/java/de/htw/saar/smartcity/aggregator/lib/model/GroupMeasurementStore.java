package de.htw.saar.smartcity.aggregator.lib.model;

import java.util.HashMap;

//Todo: check if ids or objects
public class GroupMeasurementStore<T> {

    private Long groupId;

    private HashMap<Long, Measurement<T>> producerIdMeasurementMap = new HashMap<>();

    public GroupMeasurementStore() {
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public HashMap<Long, Measurement<T>> getProducerIdMeasurementMap() {
        return producerIdMeasurementMap;
    }

    public void setProducerIdMeasurementMap(HashMap<Long, Measurement<T>> producerIdMeasurementMap) {
        this.producerIdMeasurementMap = producerIdMeasurementMap;
    }
}
