package de.htw.saar.smartcity.aggregator.lib.factory;

import de.htw.saar.smartcity.aggregator.lib.entity.Sensor;
import de.htw.saar.smartcity.aggregator.lib.model.Measurement;

public interface MeasurementFactory {

    Measurement create(Sensor sensor, String measurement);
}
