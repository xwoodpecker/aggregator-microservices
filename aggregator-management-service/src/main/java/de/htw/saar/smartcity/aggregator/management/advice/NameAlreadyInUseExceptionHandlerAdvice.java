package de.htw.saar.smartcity.aggregator.management.advice;

import de.htw.saar.smartcity.aggregator.lib.exception.NameAlreadyInUseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * The type Name already in use exception handler advice.
 */
@ControllerAdvice
public class NameAlreadyInUseExceptionHandlerAdvice {

    /**
     * Handle exception response entity.
     *
     * @param e the e
     * @return the response entity
     */
    @ExceptionHandler(NameAlreadyInUseException.class)
    public ResponseEntity handleException(NameAlreadyInUseException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }
}
