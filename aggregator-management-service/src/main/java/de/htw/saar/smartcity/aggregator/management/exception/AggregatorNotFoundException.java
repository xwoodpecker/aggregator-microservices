package de.htw.saar.smartcity.aggregator.management.exception;

/**
 * The type Aggregator not found exception.
 */
public class AggregatorNotFoundException extends NotFoundException {
    /**
     * Instantiates a new Aggregator not found exception.
     *
     * @param id the id
     */
    public AggregatorNotFoundException(Long id) {
        super("Could not find aggregator with id " + id);
    }
}
