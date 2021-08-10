package de.htw.saar.smartcity.aggregator.lib.controller;

import de.htw.saar.smartcity.aggregator.lib.broker.ActivityManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * The type Activity microservice controller.
 */
public abstract class ActivityMicroserviceController {

    private final ActivityManager activityManager;

    /**
     * Instantiates a new Activity microservice controller.
     *
     * @param activityManager the activity manager
     */
    public ActivityMicroserviceController(ActivityManager activityManager) {

        this.activityManager = activityManager;
    }

    /**
     * Metrics string.
     *
     * @return the string
     */
    @ResponseBody
    @RequestMapping(value="/metrics", produces="text/plain")
    public String metrics() {
        double activityPercentage = activityManager.getActivity() * 100;
        return "# HELP microservice_activity Activity described as a percentage of execution time compared to idle time\n"
                + "# TYPE microservice_activity gauge\n"
                + "microservice_activity " + activityPercentage;
    }
}
