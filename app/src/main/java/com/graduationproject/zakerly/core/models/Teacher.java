package com.graduationproject.zakerly.core.models;

public class Teacher extends User {


    private String specialization;
    private double price_for_hour;

    public Teacher(String specialization, double price_for_hour) {
        this.specialization = specialization;
        this.price_for_hour = price_for_hour;
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
