package de.htw.saar.smartcity.aggregator.groups.exception;

public class ProducerNotFoundException extends NotFoundException {
    public ProducerNotFoundException(Long id) {
        super("Could not find producer with " + id);
    }
}
