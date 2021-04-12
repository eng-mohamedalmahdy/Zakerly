package com.graduationproject.zakerly.core.models;

import io.realm.RealmObject;

public class Instructor extends RealmObject {

    private User user;
    private String specialization;
    private double pricePerHour;

    public Instructor() {
    }

    public Instructor(User user, String specialization, double pricePerHour) {
        this.user = user;
        this.specialization = specialization;
        this.pricePerHour = pricePerHour;
    }



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

    public double getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(double pricePerHour) {
        this.pricePerHour = pricePerHour;
    }
}
