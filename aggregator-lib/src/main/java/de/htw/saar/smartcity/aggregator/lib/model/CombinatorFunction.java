package de.htw.saar.smartcity.aggregator.lib.model;

import de.htw.saar.smartcity.aggregator.lib.exception.MeasurementException;

import java.util.Map;

@FunctionalInterface
public interface CombinatorFunction<T> {

    T apply (GroupMeasurementStore<T> groupMeasurementStore) throws MeasurementException;
}
