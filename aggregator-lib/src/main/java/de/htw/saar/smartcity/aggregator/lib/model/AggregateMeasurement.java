package de.htw.saar.smartcity.aggregator.lib.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


public class AggregateMeasurement extends Measurement implements Serializable {

    @JsonIgnore
    private Map<String, Measurement> sensorNameMeasurementMap = new HashMap<>();

    private String groupName;

    @JsonIgnore
    private BaseGroupCombinator baseGroupCombinator;


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
        return baseGroupCombinator.getName();
    }

    public BaseGroupCombinator getCombinator() {
        return baseGroupCombinator;
    }

    public void setCombinator(BaseGroupCombinator baseGroupCombinator) {
        this.baseGroupCombinator = baseGroupCombinator;
    }

    public void combine() {
        value = baseGroupCombinator.getFunction().apply(sensorNameMeasurementMap.values().stream().collect(Collectors.toList()));
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
