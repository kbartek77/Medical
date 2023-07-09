package com.bartek.Medical.Exception;

import org.springframework.http.HttpStatus;

public class VisitConflictException extends MedicalException {
    public VisitConflictException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
