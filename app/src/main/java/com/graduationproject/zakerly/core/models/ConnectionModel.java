package com.graduationproject.zakerly.core.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.graduationproject.zakerly.core.constants.RequestStatus;

public class ConnectionModel implements Parcelable {

    private String fromUid;
    private String toUid;
    private RequestStatus requestStatus;
    private boolean currentlyConnected;
    private String latestRequestUid;
    private String latestTopic;

    public ConnectionModel(String fromUid, String toUid, RequestStatus requestStatus, boolean currentlyConnected, String latestRequestUid) {
        this.fromUid = fromUid;
        this.toUid = toUid;
        this.requestStatus = requestStatus;
        this.currentlyConnected = currentlyConnected;
        this.latestRequestUid = latestRequestUid;
    }

    public ConnectionModel() {
    }

    protected ConnectionModel(Parcel in) {
        fromUid = in.readString();
        toUid = in.readString();
        currentlyConnected = in.readByte() != 0;
        latestRequestUid = in.readString();
    }

    public static final Creator<ConnectionModel> CREATOR = new Creator<ConnectionModel>() {
        @Override
        public ConnectionModel createFromParcel(Parcel in) {
            return new ConnectionModel(in);
        }

        @Override
        public ConnectionModel[] newArray(int size) {
            return new ConnectionModel[size];
        }
    };

    public String getFromUid() {
        return fromUid;
    }

    public void setFromUid(String fromUid) {
        this.fromUid = fromUid;
    }

    public String getToUid() {
        return toUid;
    }

    public void setToUid(String toUid) {
        this.toUid = toUid;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    public boolean isCurrentlyConnected() {
        return currentlyConnected;
    }

    public void setCurrentlyConnected(boolean currentlyConnected) {
        this.currentlyConnected = currentlyConnected;
    }

    public String getLatestRequestUid() {
        return latestRequestUid;
    }

    public void setLatestRequestUid(String latestRequestUid) {
        this.latestRequestUid = latestRequestUid;
    }


    public String getLatestTopic() {
        return latestTopic;
    }

    public void setLatestTopic(String latestTopic) {
        this.latestTopic = latestTopic;
    }

    public ConnectionModel swapped() {
        ConnectionModel c = this;
        String toUidTemp = c.getToUid();
        c.setToUid(fromUid);
        c.setFromUid(toUidTemp);
        return c;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fromUid);
        dest.writeString(toUid);
        dest.writeByte((byte) (currentlyConnected ? 1 : 0));
        dest.writeString(latestRequestUid);
    }

    @Override
    public String toString() {
        return "ConnectionModel{" +
                "fromUid='" + fromUid + '\'' +
                ", toUid='" + toUid + '\'' +
                ", requestStatus=" + requestStatus +
                ", currentlyConnected=" + currentlyConnected +
                ", latestRequestUid='" + latestRequestUid + '\'' +
                ", latestTopic='" + latestTopic + '\'' +
                '}';
    }
}
