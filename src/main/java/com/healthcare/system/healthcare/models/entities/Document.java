package com.healthcare.system.healthcare.models.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "documents")
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

    @Column(name = "file_name")
    private String fileName;


    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Doctor getSender() { return sender; }
    public void setSender(Doctor sender) { this.sender = sender; }

    public Patient getReceiver() { return receiver; }
    public void setReceiver(Patient receiver) { this.receiver = receiver; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Boolean getIsForPatient() { return isForPatient; }
    public void setIsForPatient(Boolean isForPatient) { this.isForPatient = isForPatient; }

    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }
}
