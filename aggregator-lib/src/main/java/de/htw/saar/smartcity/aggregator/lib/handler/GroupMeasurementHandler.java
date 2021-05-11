package de.htw.saar.smartcity.aggregator.lib.handler;

import de.htw.saar.smartcity.aggregator.lib.model.SensorGroupMeasurement;

public interface GroupMeasurementHandler {

    void handleSensorGroupMeasurement(SensorGroupMeasurement sensorGroupMeasurement);
}
