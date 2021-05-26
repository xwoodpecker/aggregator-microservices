package de.htw.saar.smartcity.aggregator.groups.exception;

public class AggregatorNotFoundException extends NotFoundException {
    public AggregatorNotFoundException(Long id) {
        super("Could not find aggregator with id " + id);
    }
}
