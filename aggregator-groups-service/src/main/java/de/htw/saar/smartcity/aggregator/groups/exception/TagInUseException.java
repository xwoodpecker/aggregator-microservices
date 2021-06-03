package de.htw.saar.smartcity.aggregator.groups.exception;

public class TagInUseException extends InUseException {

    public TagInUseException(Long id) {
        super("Tag with id " + id + "is used by a producer");
    }
}
