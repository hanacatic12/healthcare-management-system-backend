package com.healthcare.system.healthcare.models;

public class Document {
    private Integer id;
    private Integer senderId;
    private Integer receiverId;
    private String title;
    private String content;
    private String senderRole;
    private String receiverRole;
    private boolean isForPatient;

    public Document(Integer id, Integer senderId, Integer receiverId, String title, String content, boolean isForPatient, String senderRole, String receiverRole) {
        this.id = id;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.title = title;
        this.content = content;
        this.isForPatient = isForPatient;
        this.senderRole = senderRole;
        this.receiverRole = receiverRole;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSenderId() {
        return senderId;
    }

    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }

    public Integer getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Integer receiverId) {
        this.receiverId = receiverId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isForPatient() {
        return isForPatient;
    }

    public void setForPatient(boolean forPatient) {
        isForPatient = forPatient;
    }

    public String getSenderRole() {
        return senderRole;
    }

    public void setSenderRole(String senderRole) {
        this.senderRole = senderRole;
    }

    public String getReceiverRole() {
        return receiverRole;
    }

    public void setReceiverRole(String receiverRole) {
        this.receiverRole = receiverRole;
    }
}
