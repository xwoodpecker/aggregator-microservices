package de.htw.saar.smartcity.aggregator.lib.exception;

/**
 * The type Historic combinator name already in use exception.
 */
public class HistoricCombinatorNameAlreadyInUseException extends NameAlreadyInUseException {

    /**
     * Instantiates a new Historic combinator name already in use exception.
     *
     * @param name the name
     */
    public HistoricCombinatorNameAlreadyInUseException(String name)  {
        super("Historic combinator name " + name + "already in use");
    }
}
