package de.htw.saar.smartcity.aggregator.humidity.broker;

import de.htw.saar.smartcity.aggregator.lib.handler.RawMeasurementHandler;
import de.htw.saar.smartcity.aggregator.lib.properties.RawMicroserviceApplicationProperties;
import de.htw.saar.smartcity.aggregator.lib.broker.BaseReceiver;
import org.springframework.stereotype.Component;

@Component
public class HumidityBaseReceiver extends BaseReceiver {

    public HumidityBaseReceiver(RawMicroserviceApplicationProperties applicationProperties, RawMeasurementHandler rawMeasurementHandler) {
        super(applicationProperties, rawMeasurementHandler);
    }
}
