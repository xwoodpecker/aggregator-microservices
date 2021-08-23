package de.htw.saar.smartcity.aggregator.management.exception;

/**
 * The type Tag in use exception.
 */
public class TagInUseException extends InUseException {

    /**
     * Instantiates a new Tag in use exception.
     *
     * @param id the id
     */
    public TagInUseException(Long id) {
        super("Tag with id " + id + "is used by a producer");
    }
}
