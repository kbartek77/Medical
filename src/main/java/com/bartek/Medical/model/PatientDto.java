package com.bartek.Medical.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PatientDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private LocalDate birthday;
    private String idCardNo;
    private String phoneNumber;

}