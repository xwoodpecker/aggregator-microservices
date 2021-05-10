package de.htw.saar.smartcity.aggregator.lib.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.htw.saar.smartcity.aggregator.lib.entity.SensorType;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class MixedGroupMeasurement extends Measurement implements Serializable {

    @JsonIgnore
    private Map<SensorType, Measurement> sensorTypeMeasurementMap = new HashMap<>();

    private String groupName;

    @JsonIgnore
    private MixedGroupCombinator mixedGroupCombinator;


    public Map<SensorType, Measurement> getSensorNameMeasurementMap() {
        return sensorTypeMeasurementMap;
    }

    public void setSensorNameMeasurementMap(Map<SensorType, Measurement> sensorTypeMeasurementMap) {
        this.sensorTypeMeasurementMap = sensorTypeMeasurementMap;
    }



    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getAggregateName() {
        return mixedGroupCombinator.getName();
    }

    public MixedGroupCombinator getCombinator() {
        return mixedGroupCombinator;
    }

    public void setCombinator(MixedGroupCombinator mixedGroupCombinator) {
        this.mixedGroupCombinator = mixedGroupCombinator;
    }

    public void combine() {
        value = mixedGroupCombinator.getFunction().apply(sensorTypeMeasurementMap);
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
