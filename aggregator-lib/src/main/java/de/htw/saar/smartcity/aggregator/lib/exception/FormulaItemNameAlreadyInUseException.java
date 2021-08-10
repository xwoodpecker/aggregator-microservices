package de.htw.saar.smartcity.aggregator.lib.exception;

/**
 * The type Formula item name already in use exception.
 */
public class FormulaItemNameAlreadyInUseException extends NameAlreadyInUseException {

    /**
     * Instantiates a new Formula item name already in use exception.
     *
     * @param name the name
     */
    public FormulaItemNameAlreadyInUseException(String name) {
        super("Formula Item Name " + name + " already in use");
    }
}
