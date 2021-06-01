package de.htw.saar.smartcity.aggregator.groups.exception;

public class FormulaItemValueNotFoundException extends NotFoundException {
    public FormulaItemValueNotFoundException(Long id) {
        super("Could not find formula item value with id " + id);
    }
}
