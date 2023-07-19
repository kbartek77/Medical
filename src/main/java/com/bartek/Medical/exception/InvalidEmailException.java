package com.bartek.Medical.exception;

import org.springframework.http.HttpStatus;

public class InvalidEmailException extends MedicalException {
    public InvalidEmailException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
