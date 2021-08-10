package de.htw.saar.smartcity.aggregator.lib.exception;

/**
 * The type Group type name already in use exception.
 */
public class GroupTypeNameAlreadyInUseException extends NameAlreadyInUseException {

    /**
     * Instantiates a new Group type name already in use exception.
     *
     * @param name the name
     */
    public GroupTypeNameAlreadyInUseException(String name) {
        super("Group type name " + name + " already in use");
    }
}
