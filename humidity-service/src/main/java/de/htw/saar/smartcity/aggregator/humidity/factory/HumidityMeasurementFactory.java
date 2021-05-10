package de.htw.saar.smartcity.aggregator.humidity.factory;

import de.htw.saar.smartcity.aggregator.lib.entity.Sensor;
import de.htw.saar.smartcity.aggregator.lib.factory.MeasurementFactory;
import de.htw.saar.smartcity.aggregator.lib.model.BaseMeasurement;
import de.htw.saar.smartcity.aggregator.lib.model.Measurement;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class HumidityMeasurementFactory implements MeasurementFactory {

    @Override
    public Measurement create(Sensor sensor, String measurement) {

        Double humidityValue = Double.valueOf(measurement);

        BaseMeasurement<Double> m = new BaseMeasurement<>();
        m.setSensor(sensor);
        m.setTime(LocalDateTime.now());
        m.setValue(humidityValue);
        return m;
    }
}
