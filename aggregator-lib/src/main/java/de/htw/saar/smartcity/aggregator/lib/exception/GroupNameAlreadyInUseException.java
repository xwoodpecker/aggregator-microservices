package de.htw.saar.smartcity.aggregator.lib.exception;

/**
 * The type Group name already in use exception.
 */
public class GroupNameAlreadyInUseException extends NameAlreadyInUseException {
    /**
     * Instantiates a new Group name already in use exception.
     *
     * @param name the name
     */
    public GroupNameAlreadyInUseException(String name) {
        super("Group name " + name + " already in use");
    }
}
