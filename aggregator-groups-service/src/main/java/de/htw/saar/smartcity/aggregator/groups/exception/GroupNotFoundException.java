package de.htw.saar.smartcity.aggregator.groups.exception;

public class GroupNotFoundException  extends NotFoundException {

    public GroupNotFoundException(Long id) {
        super("Could not find group with id " + id);
    }
}
