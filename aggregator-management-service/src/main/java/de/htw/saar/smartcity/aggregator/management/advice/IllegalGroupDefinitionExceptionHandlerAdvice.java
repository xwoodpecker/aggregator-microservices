package de.htw.saar.smartcity.aggregator.management.advice;

import de.htw.saar.smartcity.aggregator.management.exception.IllegalGroupDefinitionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * The type Illegal group definition exception handler advice.
 */
@ControllerAdvice
public class IllegalGroupDefinitionExceptionHandlerAdvice {

    /**
     * Handle exception response entity.
     *
     * @param e the e
     * @return the response entity
     */
    @ExceptionHandler(IllegalGroupDefinitionException.class)
    public ResponseEntity handleException(IllegalGroupDefinitionException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }
}
