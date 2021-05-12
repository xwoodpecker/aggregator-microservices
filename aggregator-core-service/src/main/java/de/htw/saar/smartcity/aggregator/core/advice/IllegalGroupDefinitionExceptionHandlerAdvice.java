package de.htw.saar.smartcity.aggregator.core.advice;

import de.htw.saar.smartcity.aggregator.core.exception.GroupNotFoundException;
import de.htw.saar.smartcity.aggregator.core.exception.IllegalGroupDefinitionException;
import de.htw.saar.smartcity.aggregator.core.exception.NotFoundException;
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
