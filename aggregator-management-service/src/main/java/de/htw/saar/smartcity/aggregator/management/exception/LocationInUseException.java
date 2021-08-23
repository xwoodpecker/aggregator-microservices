package de.htw.saar.smartcity.aggregator.management.exception;

/**
 * The type Location in use exception.
 */
public class LocationInUseException extends InUseException {
    /**
     * Instantiates a new Location in use exception.
     *
     * @param id the id
     */
    public LocationInUseException(Long id) {
        super("Location with id " + id + " is used by a producer");
    }
}
