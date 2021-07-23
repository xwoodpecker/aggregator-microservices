package de.htw.saar.smartcity.aggregator.groups.exception;

public class LocationInUseException extends InUseException {
    public LocationInUseException(Long id) {
        super("Location with id " + id + " is used by a producer");
    }
}
