package com.graduationproject.zakerly.core.models;

public class Schedule2 {
   private String date, time,lessonName,studentName, instructorUid,StudentUid;

    public Schedule2(String date, String time, String lessonName, String studentName, String instructorUid, String studentUid) {
        this.date = date;
        this.time = time;
        this.lessonName = lessonName;
        this.studentName = studentName;
        this.instructorUid = instructorUid;
        StudentUid = studentUid;
    }

    public Schedule2(String date, String time, String lessonName, String studentName) {
        this.date = date;
        this.time = time;
        this.lessonName = lessonName;
        this.studentName = studentName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getInstructorUid() {
        return instructorUid;
    }

    public void setInstructorUid(String instructorUid) {
        this.instructorUid = instructorUid;
    }

    public String getStudentUid() {
        return StudentUid;
    }

    public void setStudentUid(String studentUid) {
        StudentUid = studentUid;
    }
}
