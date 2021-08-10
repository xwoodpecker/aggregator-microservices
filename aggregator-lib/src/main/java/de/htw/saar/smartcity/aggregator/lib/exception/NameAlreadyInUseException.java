package de.htw.saar.smartcity.aggregator.lib.exception;

/**
 * The type Name already in use exception.
 */
public abstract class NameAlreadyInUseException extends RuntimeException {

    /**
     * Instantiates a new Name already in use exception.
     *
     * @param message the message
     */
    public NameAlreadyInUseException(String message) {
        super(message);
    }
}