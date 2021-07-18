package de.htw.saar.smartcity.aggregator.lib.model;

import de.htw.saar.smartcity.aggregator.lib.exception.MeasurementException;

@FunctionalInterface
public interface CombinatorFunction<T, R>  {

    R apply (GroupMeasurementStore<T> groupMeasurementStore) throws MeasurementException;
}
