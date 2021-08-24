package de.htw.saar.smartcity.aggregator.humidity.aggregator.controller;

import de.htw.saar.smartcity.aggregator.lib.broker.ActivityManager;
import de.htw.saar.smartcity.aggregator.lib.controller.ActivityMicroserviceController;
import org.springframework.stereotype.Controller;

@Controller
public class HumidityAggregatorMicroserviceController extends ActivityMicroserviceController {

    public HumidityAggregatorMicroserviceController(ActivityManager activityManager) {
        super(activityManager);
    }
}
