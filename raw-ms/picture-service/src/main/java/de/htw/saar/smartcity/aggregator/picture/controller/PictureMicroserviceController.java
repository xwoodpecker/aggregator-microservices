package de.htw.saar.smartcity.aggregator.picture.controller;

import de.htw.saar.smartcity.aggregator.lib.controller.MicroserviceController;
import de.htw.saar.smartcity.aggregator.lib.properties.MicroserviceApplicationProperties;
import org.springframework.stereotype.Controller;

@Controller
public class PictureMicroserviceController extends MicroserviceController {

    protected PictureMicroserviceController(MicroserviceApplicationProperties applicationProperties) {
        super(applicationProperties);
    }
}
