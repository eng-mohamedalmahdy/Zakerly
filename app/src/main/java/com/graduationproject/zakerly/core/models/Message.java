package com.graduationproject.zakerly.core.models;

public class Message {

    String messageId;
    String senderID;
    String receiverID;
    String senderName;
    String receiverName;
    long timeOfSendMsg;
    String message;

    public Message() {
    }

    public Message(String messageId,
                   String senderID,
                   String receiverID,
                   String senderName,
                   String receiverName,
                   long timeOfSendMsg,
                   String message) {
        this.messageId = messageId;
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.timeOfSendMsg = timeOfSendMsg;
        this.message = message;
    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public long getTimeOfSendMsg() {
        return timeOfSendMsg;
    }

    public void setTimeOfSendMsg(long timeOfSendMsg) {
        this.timeOfSendMsg = timeOfSendMsg;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(String receiverID) {
        this.receiverID = receiverID;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageId='" + messageId + '\'' +
                ", senderID='" + senderID + '\'' +
                ", receiverID='" + receiverID + '\'' +
                ", senderName='" + senderName + '\'' +
                ", receiverName='" + receiverName + '\'' +
                ", timeOfSendMsg=" + timeOfSendMsg +
                ", message='" + message + '\'' +
                '}';
    }
}
