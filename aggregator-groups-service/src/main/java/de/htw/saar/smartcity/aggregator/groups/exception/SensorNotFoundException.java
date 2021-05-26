package de.htw.saar.smartcity.aggregator.groups.exception;

public class SensorNotFoundException extends NotFoundException {
        
        public SensorNotFoundException(Long id) {
            super("Could not find sensor with id " + id);
        }
    }