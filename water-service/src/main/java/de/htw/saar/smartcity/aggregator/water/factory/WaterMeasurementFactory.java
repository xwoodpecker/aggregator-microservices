package de.htw.saar.smartcity.aggregator.water.factory;

import de.htw.saar.smartcity.aggregator.lib.exception.MeasurementException;
import de.htw.saar.smartcity.aggregator.lib.factory.MeasurementFactory;
import de.htw.saar.smartcity.aggregator.lib.model.Measurement;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class WaterMeasurementFactory implements MeasurementFactory {

    public Measurement<Boolean> create(String measurement) throws MeasurementException {

        Boolean waterValue;

        waterValue = Boolean.valueOf(measurement);

        Measurement<Boolean> m = new Measurement<>();
        m.setTime(LocalDateTime.now());
        m.setValue(waterValue);
        return m;
    }
}
