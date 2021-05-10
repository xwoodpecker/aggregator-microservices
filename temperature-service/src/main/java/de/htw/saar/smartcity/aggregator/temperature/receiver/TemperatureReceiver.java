package de.htw.saar.smartcity.aggregator.temperature.receiver;

import de.htw.saar.smartcity.aggregator.lib.handler.MeasurementHandler;
import de.htw.saar.smartcity.aggregator.lib.properties.BaseMicroserviceApplicationProperties;
import de.htw.saar.smartcity.aggregator.lib.receiver.Receiver;
import org.springframework.stereotype.Component;

@Component
public class TemperatureReceiver extends Receiver {

    public TemperatureReceiver(BaseMicroserviceApplicationProperties applicationProperties, MeasurementHandler measurementHandler) {
        super(applicationProperties, measurementHandler);
    }
}
