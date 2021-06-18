package de.htw.saar.smartcity.aggregator.lib.model;

import de.htw.saar.smartcity.aggregator.lib.exception.MeasurementException;

import java.util.List;

@FunctionalInterface
public interface HistoricCombinatorFunction<T> {

    T apply (List<Measurement<T>> measurementList) throws MeasurementException;
}