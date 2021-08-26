package de.htw.saar.smartcity.aggregator.picture.controller;

import de.htw.saar.smartcity.aggregator.lib.broker.ActivityManager;
import de.htw.saar.smartcity.aggregator.lib.controller.ActivityMicroserviceController;
import org.springframework.stereotype.Controller;

@Controller
public class PictureMicroserviceController extends ActivityMicroserviceController {

    public PictureMicroserviceController(ActivityManager activityManager) {
        super(activityManager);
    }
}
