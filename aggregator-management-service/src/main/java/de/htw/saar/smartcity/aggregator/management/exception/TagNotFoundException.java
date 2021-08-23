package de.htw.saar.smartcity.aggregator.management.exception;

/**
 * The type Tag not found exception.
 */
public class TagNotFoundException extends NotFoundException {

    /**
     * Instantiates a new Tag not found exception.
     *
     * @param id the id
     */
    public TagNotFoundException(Long id) {
        super("Could not find tag with id " + id);
    }
}
