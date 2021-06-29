package de.htw.saar.smartcity.aggregator.lib.controller;

import de.htw.saar.smartcity.aggregator.lib.broker.ActivityManager;
import de.htw.saar.smartcity.aggregator.lib.properties.MicroserviceApplicationProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

public abstract class ActivityMicroserviceController {

    private final MicroserviceApplicationProperties applicationProperties;
    private final ActivityManager activityManager;

    protected ActivityMicroserviceController(MicroserviceApplicationProperties applicationProperties, ActivityManager activityManager) {

        this.applicationProperties = applicationProperties;
        this.activityManager = activityManager;
    }

    @ResponseBody
    @RequestMapping(value="/metrics", produces="text/plain")
    public String metrics() {
        double activityPercentage = activityManager.getActivity() * 100;
        return "# HELP activity Activity described as a percentage of execution time compared to idle time\n"
                + "# TYPE activity gauge\n"
                + "activity " + activityPercentage;
    }
}
