package de.htw.saar.smartcity.aggregator.management.exception;

/**
 * The type Data type not found exception.
 */
public class DataTypeNotFoundException extends NotFoundException {
    /**
     * Instantiates a new Data type not found exception.
     *
     * @param id the id
     */
    public DataTypeNotFoundException(Long id) {
        super("Could not find data type with id " + id);
    }
}
