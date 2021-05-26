package de.htw.saar.smartcity.aggregator.lib.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.htw.saar.smartcity.aggregator.lib.entity.Group;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


public class MixedTempGroupMeasurement extends TempGroupMeasurement implements Serializable {


    private Map<Long, Measurement> producerIdMeasurementMap = new HashMap<>();


    public MixedTempGroupMeasurement() {
        super();
    }

    public MixedTempGroupMeasurement(Group group) {
        super(group);
    }

    public Map<Long, Measurement> getProducerIdMeasurementMap() {
        return producerIdMeasurementMap;
    }

    public void setProducerIdMeasurementMap(Map<Long, Measurement> producerIdMeasurementMap) {
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
        final StringBuffer sb = new StringBuffer("MixedGroupMeasurement{");
        sb.append("groupCombinator=").append(groupCombinator);
        sb.append(", maximumSize=").append(maximumSize);
        sb.append(", groupId=").append(groupId);
        sb.append(", producerIdMeasurementMap=").append(producerIdMeasurementMap);
        sb.append('}');
        return sb.toString();
    }
}
