package de.htw.saar.smartcity.aggregator.lib.model;

/**
 * The type Sensor measurement - encapsulates raw measurements
 */
public class SensorMeasurement {

    private String sensorName;

    private String measurement;

    /**
     * Instantiates a new Sensor measurement.
     */
    public SensorMeasurement() {
    }

    /**
     * Instantiates a new Sensor measurement.
     *
     * @param sensorName  the sensor name
     * @param measurement the measurement
     */
    public SensorMeasurement(String sensorName, String measurement) {
        this.sensorName = sensorName;
        this.measurement = measurement;
    }

    /**
     * Gets sensor name.
     *
     * @return the sensor name
     */
    public String getSensorName() {
        return sensorName;
    }

    /**
     * Sets sensor name.
     *
     * @param sensorName the sensor name
     */
    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    /**
     * Gets measurement.
     *
     * @return the measurement
     */
    public String getMeasurement() {
        return measurement;
    }

    /**
     * Sets measurement.
     *
     * @param measurement the measurement
     */
    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }
}
