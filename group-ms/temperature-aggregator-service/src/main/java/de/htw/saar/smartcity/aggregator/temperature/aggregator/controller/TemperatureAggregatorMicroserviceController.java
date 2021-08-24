package de.htw.saar.smartcity.aggregator.temperature.aggregator.controller;

import de.htw.saar.smartcity.aggregator.lib.broker.ActivityManager;
import de.htw.saar.smartcity.aggregator.lib.controller.ActivityMicroserviceController;
import org.springframework.stereotype.Controller;

@Controller
public class TemperatureAggregatorMicroserviceController extends ActivityMicroserviceController {

    public TemperatureAggregatorMicroserviceController(ActivityManager activityManager) {
        super(activityManager);
    }
}
