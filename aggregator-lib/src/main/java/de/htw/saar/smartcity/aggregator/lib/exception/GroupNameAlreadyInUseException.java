package de.htw.saar.smartcity.aggregator.lib.exception;

public class GroupNameAlreadyInUseException extends NameAlreadyInUseException {
    public GroupNameAlreadyInUseException(String name) {
        super("Group name " + name + " already in use");
    }
}
