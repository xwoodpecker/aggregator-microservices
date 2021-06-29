package de.htw.saar.smartcity.aggregator.picture.broker;

import de.htw.saar.smartcity.aggregator.lib.broker.ActivityManager;
import de.htw.saar.smartcity.aggregator.lib.broker.BaseReceiver;
import de.htw.saar.smartcity.aggregator.lib.handler.RawMeasurementHandler;
import de.htw.saar.smartcity.aggregator.lib.properties.RawMicroserviceApplicationProperties;
import org.springframework.stereotype.Component;

@Component
public class PictureBaseReceiver extends BaseReceiver {

    public PictureBaseReceiver(RawMicroserviceApplicationProperties applicationProperties, ActivityManager activityManager, RawMeasurementHandler rawMeasurementHandler) {
        super(applicationProperties, activityManager, rawMeasurementHandler);
    }
}
