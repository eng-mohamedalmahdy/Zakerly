package com.graduationproject.zakerly.core.models;


public class ItemChat implements Comparable<ItemChat> {
    String image, name, lastMsg, uid;
    long msgTime;

    public ItemChat() {
    }

    public ItemChat(String image, String name, String lastMsg, long msgTime, String uid) {
        this.image = image;
        this.name = name;
        this.lastMsg = lastMsg;
        this.msgTime = msgTime;
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastMsg() {
        return lastMsg;
    }

    public void setLastMsg(String lastMsg) {
        this.lastMsg = lastMsg;
    }

    public long getMsgTime() {
        return msgTime;
    }

    public void setMsgTime(long msgTime) {
        this.msgTime = msgTime;
    }

    @Override
    public int compareTo(ItemChat o) {
        return Long.compare(this.msgTime, o.msgTime);
    }
}
