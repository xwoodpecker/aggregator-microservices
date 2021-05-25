package de.htw.saar.smartcity.aggregator.groups.exception;

public abstract class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }
}
