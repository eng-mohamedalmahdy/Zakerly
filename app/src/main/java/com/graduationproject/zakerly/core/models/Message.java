package com.graduationproject.zakerly.core.models;

public class Message {
    String senderID;
    long timeOfSendMsg;
    String message;

    public Message(String senderID, long timeOfSendMsg, String message) {
        this.senderID = senderID;
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
}
