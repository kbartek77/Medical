package com.bartek.Medical.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String specialization;
    private String email;
    private String password;
    private Hospital hospital;

    public void setId(long id) {
        this.id = id;
    }
    public long getId() {
        return id;
    }
}
