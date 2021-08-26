package ${package}.broker;

import de.htw.saar.smartcity.aggregator.lib.broker.ActivityManager;
import de.htw.saar.smartcity.aggregator.lib.broker.BaseReceiver;
import de.htw.saar.smartcity.aggregator.lib.handler.RawMeasurementHandler;
import de.htw.saar.smartcity.aggregator.lib.properties.RawMicroserviceApplicationProperties;
import org.springframework.stereotype.Component;

@Component
public class BaseReceiverImpl extends BaseReceiver {

    public BaseReceiverImpl(RawMicroserviceApplicationProperties applicationProperties,
                                   ActivityManager activityManager,
                                   RawMeasurementHandler rawMeasurementHandler) throws Exception {

        super(applicationProperties, activityManager, rawMeasurementHandler);

    }
}
