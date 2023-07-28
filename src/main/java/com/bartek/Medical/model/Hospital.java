package com.bartek.Medical.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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
    @OneToMany(mappedBy = "hospital")
    private Set<Doctor> doctors = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hospital hospital = (Hospital) o;
        return Objects.equals(id, hospital.id) && Objects.equals(name, hospital.name) && Objects.equals(town, hospital.town) && Objects.equals(postalCode, hospital.postalCode) && Objects.equals(street, hospital.street) && Objects.equals(numberOfBuilding, hospital.numberOfBuilding);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, town, postalCode, street, numberOfBuilding);
    }

    @Override
    public String toString() {
        return "Hospital{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", town='" + town + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", street='" + street + '\'' +
                ", numberOfBuilding='" + numberOfBuilding + '\'' +
                '}';
    }
}
