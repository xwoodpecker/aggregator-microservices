package de.htw.saar.smartcity.aggregator.humidity.controller;

import de.htw.saar.smartcity.aggregator.lib.broker.ActivityManager;
import de.htw.saar.smartcity.aggregator.lib.controller.ActivityMicroserviceController;
import org.springframework.stereotype.Controller;

@Controller
public class HumidityMicroserviceController extends ActivityMicroserviceController {

    public HumidityMicroserviceController(ActivityManager activityManager) {
        super(activityManager);
    }
}
