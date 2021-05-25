package de.htw.saar.smartcity.aggregator.humidity.broker;

import de.htw.saar.smartcity.aggregator.lib.handler.MeasurementHandler;
import de.htw.saar.smartcity.aggregator.lib.properties.BaseMicroserviceApplicationProperties;
import de.htw.saar.smartcity.aggregator.lib.broker.BaseReceiver;
import org.springframework.stereotype.Component;

@Component
public class HumidityBaseReceiver extends BaseReceiver {

    public HumidityBaseReceiver(BaseMicroserviceApplicationProperties applicationProperties, MeasurementHandler measurementHandler) {
        super(applicationProperties, measurementHandler);
    }
}
