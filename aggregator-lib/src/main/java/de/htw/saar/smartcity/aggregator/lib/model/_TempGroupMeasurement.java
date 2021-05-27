package de.htw.saar.smartcity.aggregator.lib.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
@JsonTypeInfo( use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = TempGroupMeasurement.class, name = "m")
})
public abstract class TempGroupMeasurement {

    @JsonIgnore
    protected AbstractGroupCombinator abstractGroupCombinator;

    protected Integer maximumSize;

    //@JsonIgnore
    //protected Long groupId;

    public TempGroupMeasurement() {

    }

    public TempGroupMeasurement(Integer maximumSize) {
        //this.groupId = group.getId();
        //this.maximumSize = group.getProducers().size();
        this.maximumSize = maximumSize;
    }

    @JsonIgnore
    public String getAggregateName() {
        return abstractGroupCombinator.getName();
    }

    public AbstractGroupCombinator getGroupCombinator() {
        return abstractGroupCombinator;
    }

    public void setGroupCombinator(AbstractGroupCombinator abstractGroupCombinator) {
        this.abstractGroupCombinator = abstractGroupCombinator;
    }

    public Integer getMaximumSize() {
        return maximumSize;
    }

    public void setMaximumSize(Integer maximumSize) {
        this.maximumSize = maximumSize;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
}**/
