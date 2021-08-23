package de.htw.saar.smartcity.aggregator.management.exception;

/**
 * The type Illegal group definition exception.
 */
public class IllegalGroupDefinitionException extends RuntimeException {

    /**
     * Instantiates a new Illegal group definition exception.
     */
    public IllegalGroupDefinitionException() {
        super("Group definition is not met");
    }
}
