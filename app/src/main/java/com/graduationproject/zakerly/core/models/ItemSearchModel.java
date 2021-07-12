package com.graduationproject.zakerly.core.models;

import io.realm.RealmList;

public class ItemSearchModel {

    String imageProfile, name, job;
    double rate;
    RealmList<Specialisation> specialisations;
    Instructor instructor;


    public ItemSearchModel(Instructor instructor) {
        this.imageProfile = instructor.getUser().getProfileImg();
        this.name = instructor.getUser().getFirstName() + " " + instructor.getUser().getLastName();
        this.job = instructor.getTitle();
        this.rate = instructor.getAverageRate();
        this.specialisations = instructor.getUser().getInterests();
        this.instructor = instructor;
    }

    public RealmList<Specialisation> getSpecialisations() {
        return specialisations;
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

    public Instructor getInstructor() {
        return instructor;
    }
}
