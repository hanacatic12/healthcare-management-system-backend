package com.healthcare.system.healthcare.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "departments")
@Getter
@Setter
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer did;

    @Column(name = "name")
    private String name;

    @Column(name = "floor")
    private Integer floor;

    @Column(name = "description")
    private String description;


}

