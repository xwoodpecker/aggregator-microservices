package de.htw.saar.smartcity.aggregator.controller;

import de.htw.saar.smartcity.aggregator.handler.GroupMeasurementHandler;
import de.htw.saar.smartcity.aggregator.model.SensorGroupMeasurement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public abstract class SensorGroupMeasurementController {

    private final GroupMeasurementHandler groupMeasurementHandler;

    public SensorGroupMeasurementController(GroupMeasurementHandler groupMeasurementHandler) {

        this.groupMeasurementHandler = groupMeasurementHandler;
    }

    @PostMapping("/")
    public ResponseEntity postGroupMeasurement(@RequestBody SensorGroupMeasurement sensorGroupMeasurement)
    {

        groupMeasurementHandler.handleSensorGroupMeasurement(sensorGroupMeasurement);

        return new ResponseEntity(HttpStatus.OK);
    }
}
