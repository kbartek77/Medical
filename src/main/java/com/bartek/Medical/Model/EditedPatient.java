package com.bartek.Medical.Model;

import lombok.Data;

@Data
public class EditedPatient {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    public boolean containsNull(EditedPatient editedPatient){
        if(editedPatient.getFirstName() == null || editedPatient.getLastName() == null || editedPatient.getPhoneNumber()== null || editedPatient.getEmail() == null){
            return true;
        }
        return false;
    }

}
