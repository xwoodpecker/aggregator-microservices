package de.htw.saar.smartcity.aggregator.core.exception;

public class GroupNotFoundException  extends NotFoundException {

    public GroupNotFoundException(Long id) {
        super("Could not find group with " + id);
    }
}
