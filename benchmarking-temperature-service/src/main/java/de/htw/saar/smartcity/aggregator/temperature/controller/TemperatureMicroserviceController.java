package de.htw.saar.smartcity.aggregator.temperature.controller;

import de.htw.saar.smartcity.aggregator.lib.controller.MicroserviceController;
import de.htw.saar.smartcity.aggregator.lib.properties.MicroserviceApplicationProperties;
import org.springframework.stereotype.Controller;

@Controller
public class TemperatureMicroserviceController extends MicroserviceController {

    protected TemperatureMicroserviceController(MicroserviceApplicationProperties applicationProperties) {
        super(applicationProperties);
    }
}
