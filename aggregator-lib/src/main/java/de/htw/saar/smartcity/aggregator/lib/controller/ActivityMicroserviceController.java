package de.htw.saar.smartcity.aggregator.lib.controller;

import de.htw.saar.smartcity.aggregator.lib.broker.ActivityManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

public abstract class ActivityMicroserviceController {

    private final ActivityManager activityManager;

    public ActivityMicroserviceController(ActivityManager activityManager) {

        this.activityManager = activityManager;
    }

    @ResponseBody
    @RequestMapping(value="/metrics", produces="text/plain")
    public String metrics() {
        double activityPercentage = activityManager.getActivity() * 100;
        return "# HELP microservice_activity Activity described as a percentage of execution time compared to idle time\n"
                + "# TYPE microservice_activity gauge\n"
                + "microservice_activity " + activityPercentage;
    }
}
