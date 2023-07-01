package com.bartek.Medical.ExceptionHandler;

import com.bartek.Medical.Exception.MedicalException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GeneralExceptionHandler {
    @ExceptionHandler(MedicalException.class)
    public ResponseEntity<String> handleMedicalException(MedicalException e) {
        return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
    }
}

