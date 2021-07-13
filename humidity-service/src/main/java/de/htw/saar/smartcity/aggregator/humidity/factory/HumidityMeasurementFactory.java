package de.htw.saar.smartcity.aggregator.humidity.factory;

import de.htw.saar.smartcity.aggregator.lib.exception.MeasurementException;
import de.htw.saar.smartcity.aggregator.lib.factory.MeasurementFactory;
import de.htw.saar.smartcity.aggregator.lib.model.Measurement;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class HumidityMeasurementFactory implements MeasurementFactory {

    public Measurement create(String measurement) throws MeasurementException{

        Double humidityValue;
        try {
            humidityValue = Double.valueOf(measurement);
        }catch (NumberFormatException numberFormatException) {
            throw new MeasurementException("Message does not contain a valid number");
        }

        Measurement<Double> m = new Measurement<>();
        m.setTime(LocalDateTime.now());
        m.setValue(humidityValue);
        return m;
    }
}
