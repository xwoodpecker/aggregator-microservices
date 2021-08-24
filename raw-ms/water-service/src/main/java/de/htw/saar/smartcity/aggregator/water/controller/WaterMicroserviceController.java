package de.htw.saar.smartcity.aggregator.water.controller;

import de.htw.saar.smartcity.aggregator.lib.broker.ActivityManager;
import de.htw.saar.smartcity.aggregator.lib.controller.ActivityMicroserviceController;
import de.htw.saar.smartcity.aggregator.lib.controller.MicroserviceController;
import de.htw.saar.smartcity.aggregator.lib.properties.MicroserviceApplicationProperties;
import org.springframework.stereotype.Controller;

@Controller
public class WaterMicroserviceController extends ActivityMicroserviceController {

    public WaterMicroserviceController(ActivityManager activityManager) {
        super(activityManager);
    }
}
