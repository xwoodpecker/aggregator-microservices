package de.htw.saar.smartcity.aggregator.management.exception;

/**
 * The type Producer not found exception.
 */
public class ProducerNotFoundException extends NotFoundException {
    /**
     * Instantiates a new Producer not found exception.
     *
     * @param id the id
     */
    public ProducerNotFoundException(Long id) {
        super("Could not find producer with id " + id);
    }
}
