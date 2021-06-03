package de.htw.saar.smartcity.aggregator.groups.exception;

public abstract class InUseException extends RuntimeException {

    public InUseException(String message) {
        super(message);
    }
}
