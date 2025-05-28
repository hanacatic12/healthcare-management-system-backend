package com.healthcare.system.healthcare.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "patients")
@Getter
@Setter
public class Patient {

    @Id
    private Integer pid;

    @OneToOne
    @MapsId
    @JoinColumn(name = "pid")
    private User user;

    @Column(name = "last_visit")
    private LocalDate lastVisit;

    @ManyToMany
    @JoinTable(
            name = "patient_diagnosis",
            joinColumns = @JoinColumn(name = "patient"),
            inverseJoinColumns = @JoinColumn(name = "diagnosis")
    )
    private Set<Diagnosis> diagnoses;


}

