package de.htw.saar.smartcity.aggregator.management.exception;

/**
 * The type Combinator not found exception.
 */
public class CombinatorNotFoundException extends NotFoundException {
    /**
     * Instantiates a new Combinator not found exception.
     *
     * @param id the id
     */
    public CombinatorNotFoundException(Long id) {
        super("Could not find combinator with id " + id);
    }
}
