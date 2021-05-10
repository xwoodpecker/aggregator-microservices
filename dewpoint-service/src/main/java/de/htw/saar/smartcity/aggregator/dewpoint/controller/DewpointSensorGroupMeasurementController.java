package de.htw.saar.smartcity.aggregator.dewpoint.controller;

import de.htw.saar.smartcity.aggregator.lib.controller.SensorGroupMeasurementController;
import de.htw.saar.smartcity.aggregator.lib.handler.BaseGroupMeasurementHandler;
import de.htw.saar.smartcity.aggregator.lib.handler.GroupMeasurementHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
@RequestMapping("/dewpointGroupMeasurements")
public class DewpointSensorGroupMeasurementController extends SensorGroupMeasurementController {

    public DewpointSensorGroupMeasurementController(GroupMeasurementHandler groupMeasurementHandler) {
        super(groupMeasurementHandler);
    }
}
