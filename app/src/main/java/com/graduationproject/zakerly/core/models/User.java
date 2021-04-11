package com.graduationproject.zakerly.core.models;


import io.realm.RealmObject;
import io.realm.annotations.Index;

public class User extends RealmObject {
    @Index
    private String UID;
    private String type;
    private String fName ;
    private String lName;
    private String email ;
    private String profileImg;

    public User() {
    }

    public User(String UID, String type, String fName, String lName, String email, String profileImg) {
        this.UID = UID;
        this.type = type;
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.profileImg = profileImg;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
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

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }
}
