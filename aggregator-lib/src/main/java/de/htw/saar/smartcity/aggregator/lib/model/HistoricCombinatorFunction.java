package de.htw.saar.smartcity.aggregator.lib.model;

import de.htw.saar.smartcity.aggregator.lib.exception.MeasurementException;

import java.util.List;

/**
 * The interface Historic combinator function.
 *
 * @param <T> the type parameter for input and output
 */
@FunctionalInterface
public interface HistoricCombinatorFunction<T> {

    /**
     * compute the value of type T by executing calculation on list of measurments of type T
     *
     * @param measurementList the measurement list
     * @return the result of equal type T
     * @throws MeasurementException the measurement exception
     */
    T apply (List<Measurement<T>> measurementList) throws MeasurementException;
}