package com.healthcare.system.healthcare.models.dtos;

public class DocumentDto {

    private Integer id;
    private String title;
    private String content;
    private Boolean isForPatient;
    private Integer senderId;
    private Integer receiverId;
    private String fileName;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Boolean getIsForPatient() { return isForPatient; }
    public void setIsForPatient(Boolean isForPatient) { this.isForPatient = isForPatient; }

    public Integer getSenderId() { return senderId; }
    public void setSenderId(Integer senderId) { this.senderId = senderId; }

    public Integer getReceiverId() { return receiverId; }
    public void setReceiverId(Integer receiverId) { this.receiverId = receiverId; }

    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }
}
