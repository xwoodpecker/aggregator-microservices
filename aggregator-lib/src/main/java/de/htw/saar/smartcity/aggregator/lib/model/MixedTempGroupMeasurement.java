package de.htw.saar.smartcity.aggregator.lib.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.htw.saar.smartcity.aggregator.lib.entity.Group;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


public class MixedTempGroupMeasurement extends TempGroupMeasurement implements Serializable {


    private Map<Long, Measurement> memberIdMeasurementMap = new HashMap<>();


    public MixedTempGroupMeasurement() {
        super();
    }

    public MixedTempGroupMeasurement(Group group) {
        super(group);
    }


    public Map<Long, Measurement> getMemberIdMeasurementMap() {
        return memberIdMeasurementMap;
    }

    public void setMemberIdMeasurementMap(Map<Long, Measurement> memberIdMeasurementMap) {
        this.memberIdMeasurementMap = memberIdMeasurementMap;
    }

    @JsonIgnore
    public boolean ready() {
        return maximumSize == memberIdMeasurementMap.values().size();
    }

    @JsonIgnore
    public Measurement combine() {
        Measurement m = new Measurement();
        m.setTime(LocalDateTime.now());
        m.setValue(groupCombinator.getFunction().apply(memberIdMeasurementMap));
        return m;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("MixedGroupMeasurement{");
        sb.append("groupCombinator=").append(groupCombinator);
        sb.append(", maximumSize=").append(maximumSize);
        sb.append(", groupId=").append(groupId);
        sb.append(", memberIdMeasurementMap=").append(memberIdMeasurementMap);
        sb.append('}');
        return sb.toString();
    }
}
