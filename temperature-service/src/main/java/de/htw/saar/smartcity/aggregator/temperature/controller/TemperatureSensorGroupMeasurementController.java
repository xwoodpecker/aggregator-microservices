package de.htw.saar.smartcity.aggregator.temperature.controller;

import de.htw.saar.smartcity.aggregator.controller.SensorGroupMeasurementController;
import de.htw.saar.smartcity.aggregator.temperature.handler.TemperatureGroupMeasurementHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/temperatureGroupMeasurements")
public class TemperatureSensorGroupMeasurementController extends SensorGroupMeasurementController {

    public TemperatureSensorGroupMeasurementController(TemperatureGroupMeasurementHandler temperatureGroupMeasurementHandler) {
        super(temperatureGroupMeasurementHandler);
    }

}
