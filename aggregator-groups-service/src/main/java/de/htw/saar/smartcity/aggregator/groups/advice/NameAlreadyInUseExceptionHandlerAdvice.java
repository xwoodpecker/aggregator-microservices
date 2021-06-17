package de.htw.saar.smartcity.aggregator.groups.advice;

import de.htw.saar.smartcity.aggregator.groups.exception.NotFoundException;
import de.htw.saar.smartcity.aggregator.lib.exception.NameAlreadyInUseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class NameAlreadyInUseExceptionHandlerAdvice {

    @ExceptionHandler(NameAlreadyInUseException.class)
    public ResponseEntity handleException(NotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }
}
