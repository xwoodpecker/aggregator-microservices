package de.htw.saar.smartcity.aggregator.temperature.controller;

import de.htw.saar.smartcity.aggregator.lib.broker.BrokerConnection;
import de.htw.saar.smartcity.aggregator.lib.controller.MicroserviceController;
import de.htw.saar.smartcity.aggregator.lib.properties.ApplicationProperties;
import de.htw.saar.smartcity.aggregator.temperature.broker.TemperatureBaseReceiver;
import org.springframework.stereotype.Controller;

@Controller
public class TemperatureMicroserviceController extends MicroserviceController {

    protected TemperatureMicroserviceController(ApplicationProperties applicationProperties) {
        super(applicationProperties);
    }
}
