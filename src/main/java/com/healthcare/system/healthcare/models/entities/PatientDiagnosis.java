package com.healthcare.system.healthcare.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@IdClass(PatientDiagnosisId.class)
@Table(name = "patient_diagnosis")
@Getter
@Setter
public class PatientDiagnosis {
    @Id
    @Column(name = "patient")
    private Integer patient;

    @Id
    @Column(name = "diagnosis")
    private Integer diagnosis;

    @Column(name = "diagnosed_on")
    private LocalDate diagnosedOn;
}


