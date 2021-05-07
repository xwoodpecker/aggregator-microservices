package de.htw.saar.smartcity.aggregator.temperature.receiver;

import de.htw.saar.smartcity.aggregator.receiver.Subscriber;
import de.htw.saar.smartcity.aggregator.temperature.handler.TemperatureMeasurementHandler;
import de.htw.saar.smartcity.aggregator.temperature.properties.TemperatureApplicationProperties;

public class TemperatureSubscriber extends Subscriber {

    public TemperatureSubscriber(TemperatureApplicationProperties applicationProperties, TemperatureMeasurementHandler temperatureMeasurementHandler) {
        super(applicationProperties, temperatureMeasurementHandler);
    }
}
