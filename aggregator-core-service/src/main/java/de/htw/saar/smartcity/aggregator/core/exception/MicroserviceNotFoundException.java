package de.htw.saar.smartcity.aggregator.core.exception;

public class MicroserviceNotFoundException extends NotFoundException {

        public MicroserviceNotFoundException(Long id) {
            super("Could not find microservice with " + id);
        }
    }