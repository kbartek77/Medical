package com.bartek.Medical.model;

import lombok.Data;

@Data
public class HospitalDto {
    private Long id;
    private String name;
    private String town;
    private String postalCode;
    private String street;
    private String numberOfBuilding;
}
