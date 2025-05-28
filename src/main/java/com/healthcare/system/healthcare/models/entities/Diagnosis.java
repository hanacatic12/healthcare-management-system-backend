package com.healthcare.system.healthcare.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "diagnosis")
@Getter
@Setter
public class Diagnosis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer did;

    private String name;
    private String description;

    @ManyToMany(mappedBy = "diagnoses")
    private Set<Patient> patients;

}

