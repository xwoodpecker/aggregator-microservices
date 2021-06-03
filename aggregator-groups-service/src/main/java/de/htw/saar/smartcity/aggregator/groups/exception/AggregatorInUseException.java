package de.htw.saar.smartcity.aggregator.groups.exception;

public class AggregatorInUseException extends InUseException {
    public AggregatorInUseException(Long id) {
        super("Aggregator with id " + id + " is used by a group");
    }
}
