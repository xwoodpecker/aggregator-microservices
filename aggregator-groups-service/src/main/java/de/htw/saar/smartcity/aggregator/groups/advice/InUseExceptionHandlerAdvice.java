package de.htw.saar.smartcity.aggregator.groups.advice;

import de.htw.saar.smartcity.aggregator.groups.exception.InUseException;
import de.htw.saar.smartcity.aggregator.groups.exception.TagInUseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class InUseExceptionHandlerAdvice {

    @ExceptionHandler(InUseException.class)
    public ResponseEntity handleException(InUseException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }
}
