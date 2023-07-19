package com.bartek.Medical.exception;

import org.springframework.http.HttpStatus;

public abstract class MedicalException extends RuntimeException {
    private final HttpStatus httpStatus;

    public MedicalException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}

