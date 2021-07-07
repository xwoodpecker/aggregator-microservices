package de.htw.saar.smartcity.aggregator.temperature.controller;

import de.htw.saar.smartcity.aggregator.lib.broker.ActivityManager;
import de.htw.saar.smartcity.aggregator.lib.controller.ActivityMicroserviceController;
import de.htw.saar.smartcity.aggregator.lib.properties.MicroserviceApplicationProperties;
import org.springframework.stereotype.Controller;

@Controller
public class TemperatureMicroserviceController extends ActivityMicroserviceController {

    protected TemperatureMicroserviceController(MicroserviceApplicationProperties applicationProperties, ActivityManager activityManager) {
        super(applicationProperties, activityManager);
    }
}
