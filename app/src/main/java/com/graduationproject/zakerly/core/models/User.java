package com.graduationproject.zakerly.core.models;

import android.media.Image;

import io.realm.RealmObject;
import io.realm.annotations.Index;

public class User extends RealmObject {
    @Index
    private int UID;
    private String type;
    private String fName ;
    private String lName;
    private String email ;
    private Image profileImg;

    public User() {
    }


    public int getUID() {
        return UID;
    }

    public void setUID(int UID) {
        this.UID = UID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Image getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(Image profileImg) {
        this.profileImg = profileImg;
    }
}
