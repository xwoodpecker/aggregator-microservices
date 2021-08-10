package de.htw.saar.smartcity.aggregator.lib.exception;

/**
 * The type Sensor name already in use exception.
 */
public class SensorNameAlreadyInUseException extends NameAlreadyInUseException {

    /**
     * Instantiates a new Sensor name already in use exception.
     *
     * @param name the name
     */
    public SensorNameAlreadyInUseException(String name) {
        super("Sensor name " + name  + " already in use");
    }
}
