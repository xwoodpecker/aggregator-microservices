package de.htw.saar.smartcity.aggregator.temperature.receiver;

import de.htw.saar.smartcity.aggregator.receiver.Receiver;
import de.htw.saar.smartcity.aggregator.temperature.handler.TemperatureMeasurementHandler;
import de.htw.saar.smartcity.aggregator.temperature.properties.TemperatureApplicationProperties;
import org.springframework.stereotype.Component;

@Component
public class TemperatureReceiver extends Receiver {

    public TemperatureReceiver(TemperatureApplicationProperties applicationProperties, TemperatureMeasurementHandler temperatureMeasurementHandler) {
        super(applicationProperties, temperatureMeasurementHandler);
    }
}
