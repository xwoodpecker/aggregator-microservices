package de.htw.saar.smartcity.aggregator.factory;

import de.htw.saar.smartcity.aggregator.entity.Sensor;
import de.htw.saar.smartcity.aggregator.model.Measurement;

public interface MeasurementFactory {

    Measurement create(Sensor sensor, String measurement);
}
