package de.htw.saar.smartcity.aggregator.temperature.controller;

import de.htw.saar.smartcity.aggregator.lib.broker.ActivityManager;
import de.htw.saar.smartcity.aggregator.lib.controller.ActivityMicroserviceController;
import org.springframework.stereotype.Controller;

@Controller
public class TemperatureMicroserviceController extends ActivityMicroserviceController {

    public TemperatureMicroserviceController(ActivityManager activityManager) {
        super(activityManager);
    }
}
