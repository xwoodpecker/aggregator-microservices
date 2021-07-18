package de.htw.saar.smartcity.aggregator.exporter.controller;

import de.htw.saar.smartcity.aggregator.lib.broker.ActivityManager;
import de.htw.saar.smartcity.aggregator.lib.controller.ActivityMicroserviceController;
import org.springframework.stereotype.Controller;

@Controller
public class ExporterMicroserviceController extends ActivityMicroserviceController {

    public ExporterMicroserviceController(ActivityManager activityManager) {
        super(activityManager);
    }
}
