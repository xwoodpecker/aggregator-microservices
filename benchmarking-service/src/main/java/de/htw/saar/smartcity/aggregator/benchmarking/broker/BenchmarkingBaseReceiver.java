package de.htw.saar.smartcity.aggregator.benchmarking.broker;

import de.htw.saar.smartcity.aggregator.lib.broker.ActivityManager;
import de.htw.saar.smartcity.aggregator.lib.handler.RawMeasurementHandler;
import de.htw.saar.smartcity.aggregator.lib.properties.RawMicroserviceApplicationProperties;
import de.htw.saar.smartcity.aggregator.lib.broker.BaseReceiver;
import org.springframework.stereotype.Component;

@Component
public class BenchmarkingBaseReceiver extends BaseReceiver {

    public BenchmarkingBaseReceiver(RawMicroserviceApplicationProperties applicationProperties, ActivityManager activityManager, RawMeasurementHandler rawMeasurementHandler) {
        super(applicationProperties, activityManager, rawMeasurementHandler);
    }
}
