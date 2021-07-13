package de.htw.saar.smartcity.aggregator.temperature.broker;

import de.htw.saar.smartcity.aggregator.lib.broker.ActivityManager;
import de.htw.saar.smartcity.aggregator.lib.handler.RawMeasurementHandler;
import de.htw.saar.smartcity.aggregator.lib.properties.RawMicroserviceApplicationProperties;
import de.htw.saar.smartcity.aggregator.lib.broker.BaseReceiver;
import org.springframework.stereotype.Component;

@Component
public class TemperatureBaseReceiver extends BaseReceiver {

    public TemperatureBaseReceiver(RawMicroserviceApplicationProperties applicationProperties,
                                   ActivityManager activityManager,
                                   RawMeasurementHandler rawMeasurementHandler) throws Exception {

        super(applicationProperties, activityManager, rawMeasurementHandler);

    }
}
