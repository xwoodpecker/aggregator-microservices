package de.htw.saar.smartcity.aggregator.temperature.broker;

import de.htw.saar.smartcity.aggregator.lib.handler.MeasurementHandler;
import de.htw.saar.smartcity.aggregator.lib.properties.BaseMicroserviceApplicationProperties;
import de.htw.saar.smartcity.aggregator.lib.broker.BaseReceiver;
import org.springframework.stereotype.Component;

@Component
public class TemperatureBaseReceiver extends BaseReceiver {

    public TemperatureBaseReceiver(BaseMicroserviceApplicationProperties applicationProperties, MeasurementHandler measurementHandler) {
        super(applicationProperties, measurementHandler);
    }
}
