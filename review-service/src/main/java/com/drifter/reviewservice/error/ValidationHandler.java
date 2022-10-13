package com.drifter.reviewservice.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class ValidationHandler {

    @ExceptionHandler(value = WebExchangeBindException.class)
    public ResponseEntity<Map<String, Map<String, String>>> handleException(WebExchangeBindException e) {
        log.info("Within Response exception");
        Map<String, Map<String, String>> errorObj = new HashMap<>();
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        errorObj.put("errors", errors);
        return ResponseEntity.badRequest().body(errorObj);
    }

}