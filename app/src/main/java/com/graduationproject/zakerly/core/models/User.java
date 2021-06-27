package com.graduationproject.zakerly.core.models;



import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Index;

public class User extends RealmObject {
    @Index
    private String UID;
    private String type;
    private String firstName;
    private String lastName;
    private String email;
    private String authType;
    private String profileImg;
    private RealmList<Specialisation> interests;


    public User() {
    }

    public User(String UID, String type, String firstName, String lastName, String email, String authType, String profileImg, RealmList<Specialisation> interests) {
        this.UID = UID;
        this.type = type;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.authType = authType;
        this.profileImg = profileImg;
        this.interests = interests;
    }

    public RealmList<Specialisation> getInterests() {
        return interests;
    }

    public void setInterests(ArrayList<Specialisation> interests) {
        RealmList<Specialisation> newInterests = new RealmList<>();
        newInterests.addAll(interests);
        this.interests = newInterests;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }

    @Override
    public String toString() {
        return "User{" +
                "UID='" + UID + '\'' +
                ", type='" + type + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", authType='" + authType + '\'' +
                ", profileImg='" + profileImg + '\'' +
                ", interests=" + interests +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        return UID.equals(user.UID);
    }

    @Override
    public int hashCode() {
        return UID.hashCode();
    }
}
