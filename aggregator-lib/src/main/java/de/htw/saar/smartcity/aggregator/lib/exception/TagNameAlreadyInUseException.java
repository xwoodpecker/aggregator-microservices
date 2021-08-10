package de.htw.saar.smartcity.aggregator.lib.exception;

/**
 * The type Tag name already in use exception.
 */
public class TagNameAlreadyInUseException extends NameAlreadyInUseException {

    /**
     * Instantiates a new Tag name already in use exception.
     *
     * @param name the name
     */
    public TagNameAlreadyInUseException(String name) {
        super("Tag name " + name + "already in use");
    }
}
