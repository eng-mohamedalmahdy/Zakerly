package com.graduationproject.zakerly.navigation.favorites;

public class FavoriteDataClass {

    int imageUri ;
    String teacherName;
    String teacherJob;
    String teacherDesc ;

    public FavoriteDataClass(int imageUri, String teacherName, String teacherJob, String teacherDesc) {
        this.imageUri = imageUri;
        this.teacherName = teacherName;
        this.teacherJob = teacherJob;
        this.teacherDesc = teacherDesc;
    }

    public FavoriteDataClass() {
    }

    public int getImageUri() {
        return imageUri;
    }

    public void setImageUri(int imageUri) {
        this.imageUri = imageUri;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherJob() {
        return teacherJob;
    }

    public void setTeacherJob(String teacherJob) {
        this.teacherJob = teacherJob;
    }

    public String getTeacherDesc() {
        return teacherDesc;
    }

    public void setTeacherDesc(String teacherDesc) {
        this.teacherDesc = teacherDesc;
    }
}
