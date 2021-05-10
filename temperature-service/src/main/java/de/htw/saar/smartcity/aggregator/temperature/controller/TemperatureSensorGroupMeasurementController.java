package de.htw.saar.smartcity.aggregator.temperature.controller;

import de.htw.saar.smartcity.aggregator.lib.controller.SensorGroupMeasurementController;
import de.htw.saar.smartcity.aggregator.lib.handler.BaseGroupMeasurementHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/temperatureGroupMeasurements")
public class TemperatureSensorGroupMeasurementController extends SensorGroupMeasurementController {


    public TemperatureSensorGroupMeasurementController(BaseGroupMeasurementHandler baseGroupMeasurementHandler) {
        super(baseGroupMeasurementHandler);
    }
}
