package de.htw.saar.smartcity.aggregator.groups.advice;

import de.htw.saar.smartcity.aggregator.groups.exception.IllegalGroupDefinitionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class IllegalGroupDefinitionExceptionHandlerAdvice {

    @ExceptionHandler(IllegalGroupDefinitionException.class)
    public ResponseEntity handleException(IllegalGroupDefinitionException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }
}
