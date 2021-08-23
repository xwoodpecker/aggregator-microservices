package de.htw.saar.smartcity.aggregator.management.advice;

import de.htw.saar.smartcity.aggregator.management.exception.InUseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * The type In use exception handler advice.
 */
@ControllerAdvice
public class InUseExceptionHandlerAdvice {

    /**
     * Handle exception response entity.
     *
     * @param e the e
     * @return the response entity
     */
    @ExceptionHandler(InUseException.class)
    public ResponseEntity handleException(InUseException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }
}
