package de.htw.saar.smartcity.aggregator.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


public class GroupMeasurement<T> extends Measurement implements Serializable {

    @JsonIgnore
    private Map<String, Measurement<T>> sensorNameMeasurementMap = new HashMap<>();

    private String groupName;

    @JsonIgnore
    private Combinator combinator;


    public Map<String, Measurement<T>> getSensorNameMeasurementMap() {
        return sensorNameMeasurementMap;
    }

    public void setSensorNameMeasurementMap(Map<String, Measurement<T>> sensorNameMeasurementMap) {
        this.sensorNameMeasurementMap = sensorNameMeasurementMap;
    }



    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getAggregateName() {
        return combinator.getName();
    }

    public Combinator getCombinator() {
        return combinator;
    }

    public void setCombinator(Combinator combinator) {
        this.combinator = combinator;
    }

    public void combine() {
        value = combinator.getFunction().apply(sensorNameMeasurementMap.values().stream().collect(Collectors.toList()));
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
