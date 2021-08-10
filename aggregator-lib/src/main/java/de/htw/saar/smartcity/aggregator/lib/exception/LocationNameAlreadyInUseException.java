package de.htw.saar.smartcity.aggregator.lib.exception;

/**
 * The type Location name already in use exception.
 */
public class LocationNameAlreadyInUseException extends NameAlreadyInUseException {

    /**
     * Instantiates a new Location name already in use exception.
     *
     * @param name the name
     */
    public LocationNameAlreadyInUseException(String name) {
        super("Location Name " + name + " already in use");
    }

}
