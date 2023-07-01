package com.bartek.Medical.Model;

import lombok.Data;


@Data
public class DoctorDTO {
    private String firstName;
    private String lastName;
    private String specialization;
    private String email;
}
