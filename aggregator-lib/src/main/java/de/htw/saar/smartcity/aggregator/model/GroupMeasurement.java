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

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime time;

    private String groupName;

    private String aggregateName;


    //todo: use combinator class
    @JsonIgnore
    private Function<List<Measurement<T>>, T> combinator;


    public Map<String, Measurement<T>> getSensorNameMeasurementMap() {
        return sensorNameMeasurementMap;
    }

    public void setSensorNameMeasurementMap(Map<String, Measurement<T>> sensorNameMeasurementMap) {
        this.sensorNameMeasurementMap = sensorNameMeasurementMap;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }


    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getAggregateName() {
        return aggregateName;
    }

    public void setAggregateName(String aggregateName) {
        this.aggregateName = aggregateName;
    }

    public Function<List<Measurement<T>>, T> getCombinator() {
        return combinator;
    }

    public void setCombinator(Function<List<Measurement<T>>, T> combinator) {
        this.combinator = combinator;
    }

    public void combine() {
        value = combinator.apply(sensorNameMeasurementMap.values().stream().collect(Collectors.toList()));
    }



    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("GroupMeasurement{");
        sb.append("sensorNameMeasurementMap=").append(sensorNameMeasurementMap);
        sb.append(", time=").append(time);
        sb.append(", combinator=").append(combinator);
        sb.append(", value=").append(value);
        sb.append(", time=").append(time);
        sb.append('}');
        return sb.toString();
    }
}
