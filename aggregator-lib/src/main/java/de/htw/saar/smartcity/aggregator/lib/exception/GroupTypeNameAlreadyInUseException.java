package de.htw.saar.smartcity.aggregator.lib.exception;

public class GroupTypeNameAlreadyInUseException extends NameAlreadyInUseException {

    public GroupTypeNameAlreadyInUseException(String name) {
        super("Group type name " + name + " already in use");
    }
}
