package com.graduationproject.zakerly.core.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.graduationproject.zakerly.core.constants.NotificationType;

public class NotificationData implements Parcelable {
    private long notificationTime;
    private String notificationId;
    private String title;
    private String body;
    private NotificationType notificationType;
    private String senderName;
    private String receiverName;
    private String senderUid;
    private String receiverUid;
    private String senderImageUrl;
    private int neededHours;

    public NotificationData() {
    }

    public NotificationData(long notificationTime,
                            String notificationId,
                            String title,
                            String body,
                            NotificationType notificationType,
                            String senderName,
                            String receiverName,
                            String senderUid,
                            String receiverUid,
                            String senderImageUrl,
                            int neededHours) {
        this.notificationTime = notificationTime;
        this.notificationId = notificationId;
        this.title = title;
        this.body = body;
        this.notificationType = notificationType;
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.senderUid = senderUid;
        this.receiverUid = receiverUid;
        this.senderImageUrl = senderImageUrl;
        this.neededHours = neededHours;
    }


    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    protected NotificationData(Parcel in) {
        notificationTime = in.readLong();
        notificationId = in.readString();
        title = in.readString();
        body = in.readString();
        senderName = in.readString();
        senderUid = in.readString();
        receiverUid = in.readString();
        neededHours = in.readInt();
    }

    public String getSenderImageUrl() {
        return senderImageUrl;
    }

    public void setSenderImageUrl(String senderImageUrl) {
        this.senderImageUrl = senderImageUrl;
    }

    public static final Creator<NotificationData> CREATOR = new Creator<NotificationData>() {
        @Override
        public NotificationData createFromParcel(Parcel in) {
            return new NotificationData(in);
        }

        @Override
        public NotificationData[] newArray(int size) {
            return new NotificationData[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(notificationTime);
        dest.writeString(notificationId);
        dest.writeString(title);
        dest.writeString(body);
        dest.writeString(senderName);
        dest.writeString(senderUid);
        dest.writeString(receiverUid);
        dest.writeInt(neededHours);
    }
}
