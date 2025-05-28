package com.healthcare.system.healthcare.models.dtos;

public class DocumentTransferRequest {
    private Integer senderId;
    private Integer receiverId;
    private String title;
    private String content;
    private String senderRole;
    private String receiverRole;
    private boolean isForPatient;

    public Integer getSenderId() { return senderId; }
    public Integer getReceiverId() { return receiverId; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public boolean isForPatient() { return isForPatient; }

    public void setSenderId(Integer senderId) { this.senderId = senderId; }
    public void setReceiverId(Integer receiverId) { this.receiverId = receiverId; }
    public void setTitle(String title) { this.title = title; }
    public void setContent(String content) { this.content = content; }
    public void setForPatient(boolean forPatient) { isForPatient = forPatient; }

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
