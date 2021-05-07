package de.htw.saar.smartcity.aggregator.humidity.receiver;

import de.htw.saar.smartcity.aggregator.handler.MeasurementHandler;
import de.htw.saar.smartcity.aggregator.properties.MicroserviceApplicationProperties;
import de.htw.saar.smartcity.aggregator.receiver.Receiver;
import org.springframework.stereotype.Component;

@Component
public class HumidityReceiver extends Receiver {

    public HumidityReceiver(MicroserviceApplicationProperties applicationProperties, MeasurementHandler measurementHandler) {
        super(applicationProperties, measurementHandler);
    }
}
