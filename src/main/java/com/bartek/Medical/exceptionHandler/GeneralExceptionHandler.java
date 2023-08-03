package com.bartek.Medical.exceptionHandler;

import com.bartek.Medical.exception.MedicalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
@Slf4j
@ControllerAdvice
public class GeneralExceptionHandler {
    @ExceptionHandler(MedicalException.class)
    public ResponseEntity<String> handleMedicalException(MedicalException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
    }
}

