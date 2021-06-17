package de.htw.saar.smartcity.aggregator.picture.broker;

import de.htw.saar.smartcity.aggregator.lib.broker.BaseReceiver;
import de.htw.saar.smartcity.aggregator.lib.handler.RawMeasurementHandler;
import de.htw.saar.smartcity.aggregator.lib.properties.RawMicroserviceApplicationProperties;
import org.springframework.stereotype.Component;

@Component
public class PictureBaseReceiver extends BaseReceiver {

    public PictureBaseReceiver(RawMicroserviceApplicationProperties applicationProperties, RawMeasurementHandler rawMeasurementHandler) {
        super(applicationProperties, rawMeasurementHandler);
    }
}
