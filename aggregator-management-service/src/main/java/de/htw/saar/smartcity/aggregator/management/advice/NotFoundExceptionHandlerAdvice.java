package de.htw.saar.smartcity.aggregator.management.advice;

import de.htw.saar.smartcity.aggregator.management.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * The type Not found exception handler advice.
 */
@ControllerAdvice
public class NotFoundExceptionHandlerAdvice {

    /**
     * Handle exception response entity.
     *
     * @param e the e
     * @return the response entity
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity handleException(NotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }
}
