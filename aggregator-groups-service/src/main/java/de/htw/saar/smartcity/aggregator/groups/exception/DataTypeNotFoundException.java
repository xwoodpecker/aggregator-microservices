package de.htw.saar.smartcity.aggregator.groups.exception;

public class DataTypeNotFoundException extends NotFoundException {
    public DataTypeNotFoundException(Long id) {
        super("Could not find data type with id " + id);
    }
}
