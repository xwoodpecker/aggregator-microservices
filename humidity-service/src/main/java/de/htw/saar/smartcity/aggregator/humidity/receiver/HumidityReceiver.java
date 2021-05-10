package de.htw.saar.smartcity.aggregator.humidity.receiver;

import de.htw.saar.smartcity.aggregator.lib.handler.MeasurementHandler;
import de.htw.saar.smartcity.aggregator.lib.properties.BaseMicroserviceApplicationProperties;
import de.htw.saar.smartcity.aggregator.lib.receiver.Receiver;
import org.springframework.stereotype.Component;

@Component
public class HumidityReceiver extends Receiver {

    public HumidityReceiver(BaseMicroserviceApplicationProperties applicationProperties, MeasurementHandler measurementHandler) {
        super(applicationProperties, measurementHandler);
    }
}
