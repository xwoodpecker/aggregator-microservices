package de.htw.saar.smartcity.aggregator.lib.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.stream.Collectors;

public abstract class GroupMeasurement extends Measurement{

    protected String groupName;

    @JsonIgnore
    protected GroupCombinator groupCombinator;


    public String getAggregateName() {
        return groupCombinator.getName();
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public GroupCombinator getGroupCombinator() {
        return groupCombinator;
    }

    public void setGroupCombinator(GroupCombinator groupCombinator) {
        this.groupCombinator = groupCombinator;
    }
}
