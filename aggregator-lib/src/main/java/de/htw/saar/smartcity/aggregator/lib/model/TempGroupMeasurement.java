package de.htw.saar.smartcity.aggregator.lib.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;


public class TempGroupMeasurement implements Serializable {

    @JsonIgnore
    private GroupCombinator groupCombinator;

    private Integer maximumSize;

    private HashMap<Long, Measurement> producerIdMeasurementMap = new HashMap<>();


    public TempGroupMeasurement() {
    }


    public TempGroupMeasurement(Integer maximumSize) {
        this.maximumSize = maximumSize;
    }

    @JsonIgnore
    public String getAggregateName() {
        return groupCombinator.getName();
    }

    public GroupCombinator getGroupCombinator() {
        return groupCombinator;
    }

    public void setGroupCombinator(GroupCombinator abstractGroupCombinator) {
        this.groupCombinator = abstractGroupCombinator;
    }

    public Integer getMaximumSize() {
        return maximumSize;
    }

    public void setMaximumSize(Integer maximumSize) {
        this.maximumSize = maximumSize;
    }


    public HashMap<Long, Measurement> getProducerIdMeasurementMap() {
        return producerIdMeasurementMap;
    }

    public void setProducerIdMeasurementMap(HashMap<Long, Measurement> producerIdMeasurementMap) {
        this.producerIdMeasurementMap = producerIdMeasurementMap;
    }

    @JsonIgnore
    public boolean ready() {
        return maximumSize == producerIdMeasurementMap.values().size();
    }

    @JsonIgnore
    public Measurement combine() {
        Measurement m = new Measurement();
        m.setTime(LocalDateTime.now());
        m.setValue(groupCombinator.getFunction().apply(producerIdMeasurementMap));
        return m;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("GroupMeasurement{");
        sb.append("groupCombinator=").append(groupCombinator);
        sb.append(", maximumSize=").append(maximumSize);
        sb.append(", producerIdMeasurementMap=").append(producerIdMeasurementMap);
        sb.append('}');
        return sb.toString();
    }

    public void putMeasurement(Long producerId, Measurement measurement) {
        producerIdMeasurementMap.put(producerId, measurement);
    }
}
