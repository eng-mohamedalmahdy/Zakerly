package com.graduationproject.zakerly.core.models;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;


public class Student extends RealmObject {



    private User user;
    private RealmList<String> interests ;

    public Student() {
    }

    public Student(User user, RealmList<String> interests) {
        this.user = user;
        this.interests = interests;
    }


    public RealmList<String> getInterests() {
        return interests;
    }
    public void setInterests(RealmList<String> interests) {
        this.interests = interests;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
}