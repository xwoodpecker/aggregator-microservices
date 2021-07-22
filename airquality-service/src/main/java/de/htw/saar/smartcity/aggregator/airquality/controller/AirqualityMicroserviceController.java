package de.htw.saar.smartcity.aggregator.airquality.controller;

import de.htw.saar.smartcity.aggregator.lib.broker.ActivityManager;
import de.htw.saar.smartcity.aggregator.lib.controller.ActivityMicroserviceController;
import org.springframework.stereotype.Controller;

@Controller
public class AirqualityMicroserviceController extends ActivityMicroserviceController {

    public AirqualityMicroserviceController(ActivityManager activityManager) {
        super(activityManager);
    }
}
