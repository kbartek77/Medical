package com.bartek.Medical.exception;

import org.springframework.http.HttpStatus;

public class InvalidNameHospitalException extends MedicalException{
    public InvalidNameHospitalException(String message){
        super(message, HttpStatus.BAD_REQUEST);
    }
}
