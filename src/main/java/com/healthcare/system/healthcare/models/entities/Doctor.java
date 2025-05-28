package com.healthcare.system.healthcare.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Entity
@Table(name = "doctors")
@Getter
@Setter
public class Doctor {

    @Id
    private Integer did;

    @OneToOne
    @MapsId
    @JoinColumn(name = "did")
    private User user;

    @ManyToOne
    @JoinColumn(name = "department")
    private Department department;

    private LocalTime workStart;
    private LocalTime workEnd;

}

