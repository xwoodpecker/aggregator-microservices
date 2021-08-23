package de.htw.saar.smartcity.aggregator.management.exception;

/**
 * The type Location not found exception.
 */
public class LocationNotFoundException extends NotFoundException {
    /**
     * Instantiates a new Location not found exception.
     *
     * @param id the id
     */
    public LocationNotFoundException(Long id) {
        super("Could not find location with id " + id);
    }
}
