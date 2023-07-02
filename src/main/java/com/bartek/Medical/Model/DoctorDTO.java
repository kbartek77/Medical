package com.bartek.Medical.Model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;



@Data
public class DoctorDTO {
    private long id;
    private String firstName;
    private String lastName;
    private String specialization;
    private String email;
}
