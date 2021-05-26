package de.htw.saar.smartcity.aggregator.groups.exception;

public class GroupTypeNotFoundException extends NotFoundException {
    public GroupTypeNotFoundException(Long id) {
        super("Could not find group type with id " + id);
    }
}
