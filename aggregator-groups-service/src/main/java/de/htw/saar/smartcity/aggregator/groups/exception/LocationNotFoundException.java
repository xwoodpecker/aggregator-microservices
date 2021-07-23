package de.htw.saar.smartcity.aggregator.groups.exception;

public class LocationNotFoundException extends NotFoundException {
    public LocationNotFoundException(Long id) {
        super("Could not find location with id " + id);
    }
}
