package com.graduationproject.zakerly.core.models;

public class ItemSearchModel {
    private String profileImg, firstName,lastName,ar,en;
    private int averageRate;


    public ItemSearchModel(String profileImg, String firstName, String lastName, String ar, String en, int averageRate) {
        this.profileImg = profileImg;
        this.firstName = firstName;
        this.lastName = lastName;
        this.ar = ar;
        this.en = en;
        this.averageRate = averageRate;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
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

    public String getAr() {
        return ar;
    }

    public void setAr(String ar) {
        this.ar = ar;
    }

    public String getEn() {
        return en;
    }

    public void setEn(String en) {
        this.en = en;
    }

    public int getAverageRate() {
        return averageRate;
    }

    public void setAverageRate(int averageRate) {
        this.averageRate = averageRate;
    }
}
