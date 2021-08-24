package de.htw.saar.smartcity.aggregator.picture.factory;

import de.htw.saar.smartcity.aggregator.lib.exception.MeasurementException;
import de.htw.saar.smartcity.aggregator.lib.factory.MeasurementFactory;
import de.htw.saar.smartcity.aggregator.lib.model.Measurement;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PictureMeasurementFactory implements MeasurementFactory {

    public Measurement<String> create(String measurement) throws MeasurementException {

        Measurement<String> m = new Measurement<>();
        m.setTime(LocalDateTime.now());
        m.setValue(measurement);
        return m;
    }
}
