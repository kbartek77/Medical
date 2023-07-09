package com.bartek.Medical.Exception;

import org.springframework.http.HttpStatus;
public class VisitAlreadyAssignedException extends MedicalException {
    public VisitAlreadyAssignedException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
