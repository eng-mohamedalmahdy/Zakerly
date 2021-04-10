package com.graduationproject.zakerly.core.models;

import io.realm.RealmObject;

public class Teacher  extends RealmObject {

    private User user;
    private String specialization;
    private double price_for_hour;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public double getPrice_for_hour() {
        return price_for_hour;
    }

    public void setPrice_for_hour(double price_for_hour) {
        this.price_for_hour = price_for_hour;
    }
}
