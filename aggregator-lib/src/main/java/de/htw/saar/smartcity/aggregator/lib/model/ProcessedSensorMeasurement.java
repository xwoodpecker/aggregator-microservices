package de.htw.saar.smartcity.aggregator.lib.model;

import de.htw.saar.smartcity.aggregator.lib.model.Measurement;

import java.io.Serializable;

public class ProcessedSensorMeasurement<T> implements Serializable {

    private String sensorName;

    private Measurement<T> measurement;

    public ProcessedSensorMeasurement() {
    }

    public ProcessedSensorMeasurement(String sensorName, Measurement<T> measurement) {
        this.sensorName = sensorName;
        this.measurement = measurement;
    }

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public Measurement<T> getMeasurement() {
        return measurement;
    }

    public void setMeasurement(Measurement<T> measurement) {
        this.measurement = measurement;
    }
}
