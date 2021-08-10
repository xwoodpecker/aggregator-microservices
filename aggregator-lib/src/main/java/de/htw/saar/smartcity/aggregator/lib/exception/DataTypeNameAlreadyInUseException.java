package de.htw.saar.smartcity.aggregator.lib.exception;

/**
 * The type Data type name already in use exception.
 */
public class DataTypeNameAlreadyInUseException extends NameAlreadyInUseException{

    /**
     * Instantiates a new Data type name already in use exception.
     *
     * @param name the name
     */
    public DataTypeNameAlreadyInUseException(String name) {
        super("DataType name " + name + "already in use");
    }
}
