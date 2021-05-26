package de.htw.saar.smartcity.aggregator.groups.advice;

import de.htw.saar.smartcity.aggregator.groups.exception.TagInUseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TagInUseExceptionHandlerAdvice {

    @ExceptionHandler(TagInUseException.class)
    public ResponseEntity handleException(TagInUseException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }
}
