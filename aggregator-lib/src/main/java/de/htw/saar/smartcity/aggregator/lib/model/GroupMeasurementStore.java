package de.htw.saar.smartcity.aggregator.lib.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.HashMap;

public class GroupMeasurementStore<T> implements Serializable {

    private Long groupId;

    @JsonProperty("map")
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

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("GroupMeasurementStore{");
        sb.append("groupId=").append(groupId);
        sb.append(", producerIdMeasurementMap=").append(producerIdMeasurementMap);
        sb.append('}');
        return sb.toString();
    }
}
