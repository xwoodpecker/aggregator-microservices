package de.htw.saar.smartcity.aggregator.lib.model;

import java.io.Serializable;

public class AggregationMeasurement implements Serializable {

    private Long groupId;
    private Long producerId;
    private Measurement measurement;

    public AggregationMeasurement(Long groupId, Long producerId, Measurement measurement) {
        this.groupId = groupId;
        this.producerId = producerId;
        this.measurement = measurement;
    }

    public AggregationMeasurement() {
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getProducerId() {
        return producerId;
    }

    public void setProducerId(Long producerId) {
        this.producerId = producerId;
    }

    public Measurement getMeasurement() {
        return measurement;
    }

    public void setMeasurement(Measurement measurement) {
        this.measurement = measurement;
    }
}
