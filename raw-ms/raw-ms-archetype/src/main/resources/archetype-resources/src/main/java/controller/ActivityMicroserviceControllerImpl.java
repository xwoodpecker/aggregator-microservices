package ${package}.controller;

import de.htw.saar.smartcity.aggregator.lib.broker.ActivityManager;
import de.htw.saar.smartcity.aggregator.lib.controller.ActivityMicroserviceController;
import org.springframework.stereotype.Controller;

@Controller
public class ActivityMicroserviceControllerImpl extends ActivityMicroserviceController {

    public ActivityMicroserviceControllerImpl(ActivityManager activityManager) {
        super(activityManager);
    }
}
