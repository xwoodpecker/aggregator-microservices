package de.htw.saar.smartcity.aggregator.groups.exception;

public class TagInUseException extends RuntimeException {

    public TagInUseException(Long id) {
        super("Tag is used by a producer");
    }
}
