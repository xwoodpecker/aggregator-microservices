package de.htw.saar.smartcity.aggregator.management.exception;

/**
 * The type Formula item value not found exception.
 */
public class FormulaItemValueNotFoundException extends NotFoundException {
    /**
     * Instantiates a new Formula item value not found exception.
     *
     * @param id the id
     */
    public FormulaItemValueNotFoundException(Long id) {
        super("Could not find formula item value with id " + id);
    }
}
