package de.htw.saar.smartcity.aggregator.benchmarking.controller;

import de.htw.saar.smartcity.aggregator.lib.controller.MicroserviceController;
import de.htw.saar.smartcity.aggregator.lib.properties.MicroserviceApplicationProperties;
import org.springframework.stereotype.Controller;

@Controller
public class BenchmarkingMicroserviceController extends MicroserviceController {

    protected BenchmarkingMicroserviceController(MicroserviceApplicationProperties applicationProperties) {
        super(applicationProperties);
    }
}
