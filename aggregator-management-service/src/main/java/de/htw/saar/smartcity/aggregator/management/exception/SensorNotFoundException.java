package de.htw.saar.smartcity.aggregator.management.exception;

/**
 * The type Sensor not found exception.
 */
public class SensorNotFoundException extends NotFoundException {

    /**
     * Instantiates a new Sensor not found exception.
     *
     * @param id the id
     */
    public SensorNotFoundException(Long id) {
            super("Could not find sensor with id " + id);
        }
    }