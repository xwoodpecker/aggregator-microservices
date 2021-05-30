package de.htw.saar.smartcity.aggregator.lib.factory;

import de.htw.saar.smartcity.aggregator.lib.entity.Sensor;
import de.htw.saar.smartcity.aggregator.lib.exception.MeasurementException;
import de.htw.saar.smartcity.aggregator.lib.model.Measurement;

import java.util.Optional;

public interface MeasurementFactory {

    Measurement create(String measurement) throws MeasurementException;
}
