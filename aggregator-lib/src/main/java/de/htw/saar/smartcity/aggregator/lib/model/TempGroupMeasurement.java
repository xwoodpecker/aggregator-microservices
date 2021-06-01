package de.htw.saar.smartcity.aggregator.lib.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.htw.saar.smartcity.aggregator.lib.exception.MeasurementException;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;


public class TempGroupMeasurement implements Serializable {

    @JsonIgnore
    private GroupCombinator groupCombinator;

    private Integer maximumSize;

    //private HashMap<Long, Measurement> producerIdMeasurementMap = new HashMap<>();

    @JsonProperty("store")
    private GroupMeasurementStore groupMeasurementStore = new GroupMeasurementStore();


    public TempGroupMeasurement() {
    }


    public TempGroupMeasurement(Integer maximumSize, Long groupId) {
        this.maximumSize = maximumSize;
        this.groupMeasurementStore.setGroupId(groupId);

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


    public GroupMeasurementStore getGroupMeasurementStore() {
        return groupMeasurementStore;
    }

    public void setGroupMeasurementStore(GroupMeasurementStore groupMeasurementStore) {
        this.groupMeasurementStore = groupMeasurementStore;
    }

    @JsonIgnore
    public boolean ready() {
        return maximumSize == groupMeasurementStore.getProducerIdMeasurementMap().values().size();
    }

    @JsonIgnore
    public Measurement combine() throws MeasurementException {
        Measurement m = new Measurement();
        m.setTime(LocalDateTime.now());
        m.setValue(groupCombinator.getFunction().apply(groupMeasurementStore));
        return m;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("TempGroupMeasurement{");
        sb.append("groupCombinator=").append(groupCombinator);
        sb.append(", maximumSize=").append(maximumSize);
        sb.append(", groupMeasurementStore=").append(groupMeasurementStore);
        sb.append('}');
        return sb.toString();
    }

    public void putGroupMeasurementStoreMeasurement(Long producerId, Measurement measurement) {
        groupMeasurementStore.getProducerIdMeasurementMap().put(producerId, measurement);
    }
}
