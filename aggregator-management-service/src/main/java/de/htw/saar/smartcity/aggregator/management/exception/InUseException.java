package de.htw.saar.smartcity.aggregator.management.exception;

/**
 * The type In use exception.
 */
public abstract class InUseException extends RuntimeException {

    /**
     * Instantiates a new In use exception.
     *
     * @param message the message
     */
    public InUseException(String message) {
        super(message);
    }
}
