package com.bartek.Medical.Exception;

import org.springframework.http.HttpStatus;

public class VisitNotFoundException extends MedicalException {
    public VisitNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
