package de.htw.saar.smartcity.aggregator.lib.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.htw.saar.smartcity.aggregator.lib.entity.Group;
import org.hibernate.annotations.Type;

import java.util.stream.Collectors;

@JsonTypeInfo( use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = MixedTempGroupMeasurement.class, name = "m")
})
public abstract class TempGroupMeasurement {

    @JsonIgnore
    protected GroupCombinator groupCombinator;

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
        return groupCombinator.getName();
    }

    public GroupCombinator getGroupCombinator() {
        return groupCombinator;
    }

    public void setGroupCombinator(GroupCombinator groupCombinator) {
        this.groupCombinator = groupCombinator;
    }

    public Integer getMaximumSize() {
        return maximumSize;
    }

    public void setMaximumSize(Integer maximumSize) {
        this.maximumSize = maximumSize;
    }

    /**public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }**/
}
