package de.htw.saar.smartcity.aggregator.management.exception;

/**
 * The type Not found exception.
 */
public abstract class NotFoundException extends RuntimeException {

    /**
     * Instantiates a new Not found exception.
     *
     * @param message the message
     */
    public NotFoundException(String message) {
        super(message);
    }
}
