package de.htw.saar.smartcity.aggregator.lib.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


public class GroupMeasurement extends Measurement implements Serializable {

    @JsonIgnore
    private Map<String, Measurement> sensorNameMeasurementMap = new HashMap<>();

    private String groupName;

    @JsonIgnore
    private GroupCombinator groupCombinator;


    public Map<String, Measurement> getSensorNameMeasurementMap() {
        return sensorNameMeasurementMap;
    }

    public void setSensorNameMeasurementMap(Map<String, Measurement> sensorNameMeasurementMap) {
        this.sensorNameMeasurementMap = sensorNameMeasurementMap;
    }



    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getAggregateName() {
        return groupCombinator.getName();
    }

    public GroupCombinator getCombinator() {
        return groupCombinator;
    }

    public void setCombinator(GroupCombinator groupCombinator) {
        this.groupCombinator = groupCombinator;
    }

    public void combine() {
        value = groupCombinator.getFunction().apply(sensorNameMeasurementMap.values().stream().collect(Collectors.toList()));
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
