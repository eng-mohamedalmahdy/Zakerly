package com.graduationproject.zakerly.core.models;

import io.realm.RealmList;
import io.realm.RealmObject;

public class Instructor extends RealmObject {

    private User user;
    private double pricePerHour;

    public Instructor() {
    }

    public Instructor(User user, double pricePerHour) {
        this.user = user;
        this.pricePerHour = pricePerHour;
    }



    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(double pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    @Override
    public String toString() {
        return "Instructor{" +
                "user=" + user +
                ", pricePerHour=" + pricePerHour +
                '}';
    }
}
