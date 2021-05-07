package de.htw.saar.smartcity.aggregator.humidity.factory;

import de.htw.saar.smartcity.aggregator.entity.Sensor;
import de.htw.saar.smartcity.aggregator.factory.MeasurementFactory;
import de.htw.saar.smartcity.aggregator.model.BaseMeasurement;
import de.htw.saar.smartcity.aggregator.model.Measurement;
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
