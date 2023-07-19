package com.bartek.Medical.model;

import lombok.Data;



@Data
public class DoctorDTO {
    private long id;
    private String firstName;
    private String lastName;
    private String specialization;
    private String email;
}
