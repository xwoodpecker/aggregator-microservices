package de.htw.saar.smartcity.aggregator.airquality.aggregator.controller;

import de.htw.saar.smartcity.aggregator.lib.broker.ActivityManager;
import de.htw.saar.smartcity.aggregator.lib.controller.ActivityMicroserviceController;
import org.springframework.stereotype.Controller;

@Controller
public class AirqualityAggregatorMicroserviceController extends ActivityMicroserviceController {

    public AirqualityAggregatorMicroserviceController(ActivityManager activityManager) {
        super(activityManager);
    }
}
