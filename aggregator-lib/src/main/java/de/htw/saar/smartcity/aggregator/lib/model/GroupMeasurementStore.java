package de.htw.saar.smartcity.aggregator.lib.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.HashMap;

/**
 * The type Group measurement store.
 *
 * @param <T> the type parameter
 */
public class GroupMeasurementStore<T> implements Serializable {

    private Long groupId;

    @JsonProperty("map")
    private HashMap<Long, Measurement<T>> producerIdMeasurementMap = new HashMap<>();

    /**
     * Instantiates a new Group measurement store.
     */
    public GroupMeasurementStore() {
    }

    /**
     * Gets group id.
     *
     * @return the group id
     */
    public Long getGroupId() {
        return groupId;
    }

    /**
     * Sets group id.
     *
     * @param groupId the group id
     */
    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    /**
     * Gets producer id measurement map.
     *
     * @return the producer id measurement map
     */
    public HashMap<Long, Measurement<T>> getProducerIdMeasurementMap() {
        return producerIdMeasurementMap;
    }

    /**
     * Sets producer id measurement map.
     *
     * @param producerIdMeasurementMap the producer id measurement map
     */
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
