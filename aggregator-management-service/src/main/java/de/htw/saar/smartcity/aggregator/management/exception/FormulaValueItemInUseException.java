package de.htw.saar.smartcity.aggregator.management.exception;

/**
 * The type Formula value item in use exception.
 */
public class FormulaValueItemInUseException extends InUseException {
    /**
     * Instantiates a new Formula value item in use exception.
     *
     * @param id the id
     */
    public FormulaValueItemInUseException(Long id) {
        super("Formula value item with id " + id + " is used by a group");
    }
}
