package com.bartek.Medical.exception;

import org.springframework.http.HttpStatus;
public class VisitAlreadyAssignedException extends MedicalException {
    public VisitAlreadyAssignedException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
