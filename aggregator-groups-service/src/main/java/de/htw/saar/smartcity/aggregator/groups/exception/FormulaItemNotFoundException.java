package de.htw.saar.smartcity.aggregator.groups.exception;

public class FormulaItemNotFoundException extends NotFoundException {
    public FormulaItemNotFoundException(Long id) {
        super("Could not find formula item with id " + id);
    }
}
