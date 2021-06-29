package com.graduationproject.zakerly.core.models;

public class StudentModel {

    private String image;
    private String name;
    private String classOfStudent;

    public StudentModel() {
    }

    public StudentModel(String image, String name, String classOfStudent) {
        this.image = image;
        this.name = name;
        this.classOfStudent = classOfStudent;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassOfStudent() {
        return classOfStudent;
    }

    public void setClassOfStudent(String classOfStudent) {
        this.classOfStudent = classOfStudent;
    }
}
