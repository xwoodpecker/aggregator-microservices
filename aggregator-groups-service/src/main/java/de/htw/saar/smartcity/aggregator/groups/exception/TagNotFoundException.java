package de.htw.saar.smartcity.aggregator.groups.exception;

public class TagNotFoundException extends NotFoundException {

    public TagNotFoundException(Long id) {
        super("Could not find sensor with id" + id);
    }
}