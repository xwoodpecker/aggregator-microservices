package de.htw.saar.smartcity.aggregator.humidity.factory;

import de.htw.saar.smartcity.aggregator.lib.entity.Sensor;
import de.htw.saar.smartcity.aggregator.lib.factory.MeasurementFactory;
import de.htw.saar.smartcity.aggregator.lib.model.Measurement;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class HumidityMeasurementFactory implements MeasurementFactory {

    @Override
    public Measurement create(String measurement) {

        Double humidityValue = Double.valueOf(measurement);

        Measurement<Double> m = new Measurement<>();
        m.setTime(LocalDateTime.now());
        m.setValue(humidityValue);
        return m;
    }
}
