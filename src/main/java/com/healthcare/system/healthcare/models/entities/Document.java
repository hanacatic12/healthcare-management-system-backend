package com.healthcare.system.healthcare.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "documents")
@Getter
@Setter
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "sender", referencedColumnName = "did")
    private Doctor sender;

    @ManyToOne
    @JoinColumn(name = "receiver", referencedColumnName = "pid")
    private Patient receiver;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "is_for_patient")
    private Boolean isForPatient;
} 