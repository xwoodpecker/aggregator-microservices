package de.htw.saar.smartcity.aggregator.groups.exception;

public class FormulaValueItemInUseException extends InUseException {
    public FormulaValueItemInUseException(Long id) {
        super("Formula value item with id " + id + " is used by a group");
    }
}
