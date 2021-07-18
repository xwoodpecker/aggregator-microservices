package de.htw.saar.smartcity.aggregator.water.aggregator.controller;

import de.htw.saar.smartcity.aggregator.lib.broker.ActivityManager;
import de.htw.saar.smartcity.aggregator.lib.controller.ActivityMicroserviceController;
import org.springframework.stereotype.Controller;

@Controller
public class WaterAggregatorMicroserviceController extends ActivityMicroserviceController {


    public WaterAggregatorMicroserviceController(ActivityManager activityManager) {
        super(activityManager);
    }
}
