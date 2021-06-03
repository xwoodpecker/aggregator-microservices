package de.htw.saar.smartcity.aggregator.groups.exception;

public class TagNotFoundException extends NotFoundException {

    public TagNotFoundException(Long id) {
        super("Could not find tag with id " + id);
    }
}
