package com.bartek.Medical.exception;

import org.springframework.http.HttpStatus;

public class VisitConflictException extends MedicalException {
    public VisitConflictException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
