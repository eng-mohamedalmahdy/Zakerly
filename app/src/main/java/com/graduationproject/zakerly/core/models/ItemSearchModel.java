package com.graduationproject.zakerly.core.models;

public class ItemSearchModel {

    String imageProfile,name,job;
    double rate;

    public ItemSearchModel(String imageProfile, String name, String job, double rate) {
        this.imageProfile = imageProfile;
        this.name = name;
        this.job = job;
        this.rate = rate;
    }

    public ItemSearchModel() {
    }

    public String getImageProfile() {
        return imageProfile;
    }

    public void setImageProfile(String imageProfile) {
        this.imageProfile = imageProfile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}
