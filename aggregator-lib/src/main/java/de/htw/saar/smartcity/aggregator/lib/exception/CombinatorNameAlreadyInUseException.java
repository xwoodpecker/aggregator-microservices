package de.htw.saar.smartcity.aggregator.lib.exception;

public class CombinatorNameAlreadyInUseException extends NameAlreadyInUseException {

    public CombinatorNameAlreadyInUseException(String name) {
        super("Combinator name " + name + "already in use");
    }
}
