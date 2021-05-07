package de.htw.saar.smartcity.aggregator.model;

public class SensorMeasurement {

    private String sensorName;

    private String measurement;

    public SensorMeasurement() {
    }

    public SensorMeasurement(String sensorName, String measurement) {
        this.sensorName = sensorName;
        this.measurement = measurement;
    }

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }
}
