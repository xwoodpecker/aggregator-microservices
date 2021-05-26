package de.htw.saar.smartcity.aggregator.groups.exception;

public class CombinatorNotFoundException extends NotFoundException {
    public CombinatorNotFoundException(Long id) {
        super("Could not find combinator with id " + id);
    }
}
