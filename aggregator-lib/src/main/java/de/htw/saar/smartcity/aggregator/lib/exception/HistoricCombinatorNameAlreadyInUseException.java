package de.htw.saar.smartcity.aggregator.lib.exception;

public class HistoricCombinatorNameAlreadyInUseException extends NameAlreadyInUseException {

    public HistoricCombinatorNameAlreadyInUseException(String name)  {
        super("Historic combinator name " + name + "already in use");
    }
}
