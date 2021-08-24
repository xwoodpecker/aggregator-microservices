package de.htw.saar.smartcity.aggregator.dewpoint.controller;

import de.htw.saar.smartcity.aggregator.lib.broker.ActivityManager;
import de.htw.saar.smartcity.aggregator.lib.controller.ActivityMicroserviceController;
import org.springframework.stereotype.Controller;

@Controller
public class DewpointMicroserviceController extends ActivityMicroserviceController {

    public DewpointMicroserviceController(ActivityManager activityManager) {
        super(activityManager);
    }
}
