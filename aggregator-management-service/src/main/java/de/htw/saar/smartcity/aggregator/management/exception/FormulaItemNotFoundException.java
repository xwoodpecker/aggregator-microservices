package de.htw.saar.smartcity.aggregator.management.exception;

/**
 * The type Formula item not found exception.
 */
public class FormulaItemNotFoundException extends NotFoundException {
    /**
     * Instantiates a new Formula item not found exception.
     *
     * @param id the id
     */
    public FormulaItemNotFoundException(Long id) {
        super("Could not find formula item with id " + id);
    }
}
