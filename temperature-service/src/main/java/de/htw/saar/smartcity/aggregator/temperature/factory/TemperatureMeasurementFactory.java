package de.htw.saar.smartcity.aggregator.temperature.factory;

import de.htw.saar.smartcity.aggregator.entity.Sensor;
import de.htw.saar.smartcity.aggregator.factory.MeasurementFactory;
import de.htw.saar.smartcity.aggregator.model.BaseMeasurement;
import de.htw.saar.smartcity.aggregator.model.Measurement;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TemperatureMeasurementFactory implements MeasurementFactory {
    @Override
    public Measurement<Double> create(Sensor sensor, String message) {

        Double temperatureValue = Double.valueOf(message);

        BaseMeasurement<Double> m = new BaseMeasurement<>();
        m.setSensor(sensor);
        m.setTime(LocalDateTime.now());
        m.setValue(temperatureValue);
        return m;
    }
}
