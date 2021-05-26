package de.htw.saar.smartcity.aggregator.lib.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.htw.saar.smartcity.aggregator.lib.entity.Group;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class BaseTempGroupMeasurement extends TempGroupMeasurement implements Serializable {

    @JsonIgnore
    private List<Measurement> measurements = new ArrayList<>();

    public BaseTempGroupMeasurement(Integer maximumSize) {
        super(maximumSize);
    }

    public List<Measurement> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(List<Measurement> measurements) {
        this.measurements = measurements;
    }

    public Object combine() {
        return groupCombinator.getFunction().apply(measurements);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("BaseGroupMeasurement{");
        sb.append("measurements=").append(measurements);
        sb.append(", groupCombinator=").append(groupCombinator);
        sb.append('}');
        return sb.toString();
    }
}
