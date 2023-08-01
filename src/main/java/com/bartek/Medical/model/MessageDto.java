package com.bartek.Medical.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class MessageDto {

    private String message;
    private HttpStatus httpStatus;
    private String label;
}
