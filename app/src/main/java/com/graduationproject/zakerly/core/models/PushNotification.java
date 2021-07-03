package com.graduationproject.zakerly.core.models;

public class PushNotification {
    private NotificationData data;
    private String to;

    public PushNotification(NotificationData data, String to) {
        this.data = data;
        this.to = to;
    }

    public PushNotification() {
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
