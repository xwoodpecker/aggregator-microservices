package de.htw.saar.smartcity.aggregator.lib.exception;

public class DataTypeNameAlreadyInUseException extends NameAlreadyInUseException{

    public DataTypeNameAlreadyInUseException(String name) {
        super("DataType name " + name + "already in use");
    }
}
