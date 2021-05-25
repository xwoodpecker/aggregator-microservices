package de.htw.saar.smartcity.aggregator.groups.exception;

public class GroupMemberNotFoundException extends NotFoundException {
    public GroupMemberNotFoundException(Long id) {
        super("Could not find group member with " + id);
    }
}
