package de.htw.saar.smartcity.aggregator.lib.exception;

/**
 * The type Combinator name already in use exception.
 */
public class CombinatorNameAlreadyInUseException extends NameAlreadyInUseException {

    /**
     * Instantiates a new Combinator name already in use exception.
     *
     * @param name the name
     */
    public CombinatorNameAlreadyInUseException(String name) {
        super("Combinator name " + name + "already in use");
    }
}
