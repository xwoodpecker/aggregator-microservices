package de.htw.saar.smartcity.aggregator.lib.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class MixedGroupMeasurement extends GroupMeasurement implements Serializable {

    @JsonIgnore
    private Map<Long, Measurement> sensorTypeMeasurementMap = new HashMap<>();

    public Map<Long, Measurement> getSensorTypeMeasurementMap() {
        return sensorTypeMeasurementMap;
    }

    public void setSensorTypeMeasurementMap(Map<Long, Measurement> sensorTypeMeasurementMap) {
        this.sensorTypeMeasurementMap = sensorTypeMeasurementMap;
    }

    public void combine() {
        value = groupCombinator.getFunction().apply(sensorTypeMeasurementMap);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("GroupMeasurement{");
        sb.append("groupName='").append(groupName).append('\'');
        sb.append(", aggregateName='").append(getAggregateName()).append('\'');
        sb.append(", value=").append(value);
        sb.append(", time=").append(time);
        sb.append('}');
        return sb.toString();
    }
}
