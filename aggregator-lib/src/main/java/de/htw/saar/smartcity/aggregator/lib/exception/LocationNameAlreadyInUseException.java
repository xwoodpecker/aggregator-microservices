package de.htw.saar.smartcity.aggregator.lib.exception;

public class LocationNameAlreadyInUseException extends NameAlreadyInUseException {

    public LocationNameAlreadyInUseException(String name) {
        super("Location Name " + name + " already in use");
    }

}
