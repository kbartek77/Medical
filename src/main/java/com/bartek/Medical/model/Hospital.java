package com.bartek.Medical.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Hospital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String town;
    private String postalCode;
    private String street;
    private String numberOfBuilding;
    @ManyToMany(mappedBy = "Hospitals")
    private List<Doctor> doctors = new LinkedList<>();

}
