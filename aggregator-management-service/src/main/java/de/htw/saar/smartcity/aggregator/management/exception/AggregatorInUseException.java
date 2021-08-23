package de.htw.saar.smartcity.aggregator.management.exception;

/**
 * The type Aggregator in use exception.
 */
public class AggregatorInUseException extends InUseException {
    /**
     * Instantiates a new Aggregator in use exception.
     *
     * @param id the id
     */
    public AggregatorInUseException(Long id) {
        super("Aggregator with id " + id + " is used by a group");
    }
}
