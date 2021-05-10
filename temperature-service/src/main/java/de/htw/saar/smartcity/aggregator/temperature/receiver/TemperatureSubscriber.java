package de.htw.saar.smartcity.aggregator.temperature.receiver;

import de.htw.saar.smartcity.aggregator.temperature.properties.TemperatureApplicationProperties;
import de.htw.saar.smartcity.aggregator.lib.receiver.Subscriber;
import de.htw.saar.smartcity.aggregator.temperature.handler.TemperatureMeasurementHandler;

public class TemperatureSubscriber extends Subscriber {

    public TemperatureSubscriber(TemperatureApplicationProperties applicationProperties, TemperatureMeasurementHandler temperatureMeasurementHandler) {
        super(applicationProperties, temperatureMeasurementHandler);
    }
}
