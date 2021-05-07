package de.htw.saar.smartcity.aggregator.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import de.htw.saar.smartcity.aggregator.entity.Sensor;
import java.io.Serializable;


public class BaseMeasurement<T> extends Measurement<T> implements Serializable {

    @JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="name")
    @JsonIdentityReference(alwaysAsId=true)
    private Sensor sensor;


    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("BaseMeasurement{");
        sb.append("sensor=").append(sensor);
        sb.append(", value=").append(value);
        sb.append(", time=").append(time);
        sb.append('}');
        return sb.toString();
    }
}
