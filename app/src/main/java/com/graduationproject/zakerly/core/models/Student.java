package com.graduationproject.zakerly.core.models;

import java.util.List;

public class Student  extends User {

    private List<String> interests ;


    public Student(List<String> interests) {
        this.interests = interests;
    }

    public List<String> getInterests() {
        return interests;
    }

    public void setInterests(List<String> interests) {
        this.interests = interests;
    }
}