package de.htw.saar.smartcity.aggregator.core.exception;

public class IllegalGroupDefinitionException extends RuntimeException {

    public IllegalGroupDefinitionException() {
        super("Group definition is not met");
    }
}
