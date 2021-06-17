package de.htw.saar.smartcity.aggregator.lib.exception;

public class FormulaItemNameAlreadyInUseException extends NameAlreadyInUseException {

    public FormulaItemNameAlreadyInUseException(String name) {
        super("Formula Item Name " + name + " already in use");
    }
}
