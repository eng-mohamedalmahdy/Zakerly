package com.graduationproject.zakerly.core.models;

import com.graduationproject.zakerly.core.constants.NotificationType;

public class PushNotification {
    private NotificationData data;
    private String to;

    public PushNotification(NotificationData data, String to) {
        this.data = data;
        this.to = to;
    }

    public PushNotification() {
    }

    public PushNotification(Message message, String to) {
        data = new NotificationData(message.getTimeOfSendMsg(),
                message.getMessageId(),
                message.senderName,
                message.getMessage(), NotificationType.MESSAGE,
                message.senderName,
                message.receiverName,
                message.senderID,
                message.receiverID,
                "",
                -1
        );
        this.to = to;
    }

    public NotificationData getData() {
        return data;
    }

    public void setData(NotificationData data) {
        this.data = data;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    @Override
    public String toString() {
        return "PushNotification{" +
                "data=" + data +
                ", to='" + to + '\'' +
                '}';
    }
}

