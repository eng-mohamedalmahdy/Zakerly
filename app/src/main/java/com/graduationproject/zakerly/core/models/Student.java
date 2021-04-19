package com.graduationproject.zakerly.core.models;


import io.realm.RealmList;
import io.realm.RealmObject;


public class Student extends RealmObject {



    private User user;

    public Student() {
    }

    public Student(User user) {
        this.user = user;
    }


    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Student{" +
                "user=" + user +
                '}';
    }
}