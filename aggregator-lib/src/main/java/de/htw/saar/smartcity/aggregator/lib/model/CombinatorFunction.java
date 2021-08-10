package de.htw.saar.smartcity.aggregator.lib.model;

import de.htw.saar.smartcity.aggregator.lib.exception.MeasurementException;

/**
 * The interface Combinator function.
 *
 * @param <T> the type parameter for input
 * @param <R> the type parameter for output
 */
@FunctionalInterface
public interface CombinatorFunction<T, R>  {

    /**
     * Apply groupMeasurementStore of type T and get result of type R.
     *
     * @param groupMeasurementStore the group measurement store
     * @return the result
     * @throws MeasurementException the measurement exception
     */
    R apply (GroupMeasurementStore<T> groupMeasurementStore) throws MeasurementException;
}
