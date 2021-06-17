package de.htw.saar.smartcity.aggregator.lib.exception;

public class SensorNameAlreadyInUseException extends NameAlreadyInUseException {

    public SensorNameAlreadyInUseException(String name) {
        super("Sensor name " + name  + " already in use");
    }
}
