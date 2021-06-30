package com.graduationproject.zakerly.core.models;

import com.graduationproject.zakerly.core.constants.NotificationType;

public class NotificationData {
    private long notificationTime;
    private String notificationId;
    private String title;
    private String body;
    private NotificationType notificationType;
    private String senderName;
    private String senderUid;
    private String receiverUid;
    private int neededHours;

    public NotificationData(long notificationTime, String notificationId, String title, String body, NotificationType notificationType, String senderName, String senderUid, String receiverUid, int neededHours) {
        this.notificationTime = notificationTime;
        this.notificationId = notificationId;
        this.title = title;
        this.body = body;
        this.notificationType = notificationType;
        this.senderName = senderName;
        this.senderUid = senderUid;
        this.receiverUid = receiverUid;
        this.neededHours = neededHours;
    }

    public long getNotificationTime() {
        return notificationTime;
    }

    public void setNotificationTime(long notificationTime) {
        this.notificationTime = notificationTime;
    }

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderUid() {
        return senderUid;
    }

    public void setSenderUid(String senderUid) {
        this.senderUid = senderUid;
    }

    public String getReceiverUid() {
        return receiverUid;
    }

    public void setReceiverUid(String receiverUid) {
        this.receiverUid = receiverUid;
    }

    public int getNeededHours() {
        return neededHours;
    }

    public void setNeededHours(int neededHours) {
        this.neededHours = neededHours;
    }

    @Override
    public String toString() {
        return "NotificationData{" +
                "notificationTime=" + notificationTime +
                ", notificationId='" + notificationId + '\'' +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", notificationType=" + notificationType +
                ", senderName='" + senderName + '\'' +
                ", senderUid='" + senderUid + '\'' +
                ", receiverUid='" + receiverUid + '\'' +
                ", neededHours=" + neededHours +
                '}';
    }
}
