package de.htw.saar.smartcity.aggregator.lib.exception;

public abstract class NameAlreadyInUseException extends RuntimeException {

    public NameAlreadyInUseException(String message) {
        super(message);
    }
}