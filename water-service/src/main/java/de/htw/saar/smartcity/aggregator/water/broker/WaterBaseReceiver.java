package de.htw.saar.smartcity.aggregator.water.broker;

import de.htw.saar.smartcity.aggregator.lib.broker.BaseReceiver;
import de.htw.saar.smartcity.aggregator.lib.handler.RawMeasurementHandler;
import de.htw.saar.smartcity.aggregator.lib.properties.RawMicroserviceApplicationProperties;
import org.springframework.stereotype.Component;

@Component
public class WaterBaseReceiver extends BaseReceiver {

    public WaterBaseReceiver(RawMicroserviceApplicationProperties applicationProperties, RawMeasurementHandler rawMeasurementHandler) {
        super(applicationProperties, rawMeasurementHandler);
    }
}
