package de.htw.saar.smartcity.aggregator.temperature.factory;

import de.htw.saar.smartcity.aggregator.lib.exception.MeasurementException;
import de.htw.saar.smartcity.aggregator.lib.factory.MeasurementFactory;
import de.htw.saar.smartcity.aggregator.lib.model.Measurement;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TemperatureMeasurementFactory implements MeasurementFactory {

    public Measurement<Double> create(String measurement) throws MeasurementException {

        Double temperatureValue;

        try {
            temperatureValue = Double.valueOf(measurement);
        } catch (NumberFormatException numberFormatException) {
            throw new MeasurementException("Message does not contain a valid number");
        }

        Measurement<Double> m = new Measurement<>();
        m.setTime(LocalDateTime.now());
        m.setValue(temperatureValue);
        return m;
    }
}
