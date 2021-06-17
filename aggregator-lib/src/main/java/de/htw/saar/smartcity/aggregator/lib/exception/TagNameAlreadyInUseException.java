package de.htw.saar.smartcity.aggregator.lib.exception;

public class TagNameAlreadyInUseException extends NameAlreadyInUseException {

    public TagNameAlreadyInUseException(String name) {
        super("Tag name " + name + "already in use");
    }
}
