package com.bartek.Medical.Exception;

import org.springframework.http.HttpStatus;

public class InvalidDateTimeException extends MedicalException {
    public InvalidDateTimeException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
