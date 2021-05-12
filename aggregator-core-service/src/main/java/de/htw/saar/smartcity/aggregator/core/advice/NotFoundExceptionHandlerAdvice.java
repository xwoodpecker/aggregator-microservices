package de.htw.saar.smartcity.aggregator.core.advice;

import de.htw.saar.smartcity.aggregator.core.exception.GroupNotFoundException;
import de.htw.saar.smartcity.aggregator.core.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class NotFoundExceptionHandlerAdvice {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity handleException(GroupNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }
}
