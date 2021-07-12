package com.graduationproject.zakerly.core.models;

public class Schedule {

    private String id;
    private long scheduleTime;
    private String studentUid;
    private String instructorUid;
    private String studentName;
    private String instructorName;
    private String lessonName;

    public Schedule() {
    }

    public Schedule(String id, long scheduleTime, String studentUid, String instructorUid, String studentName, String instructorName, String lessonName) {
        this.scheduleTime = scheduleTime;
        this.studentUid = studentUid;
        this.instructorUid = instructorUid;
        this.studentName = studentName;
        this.instructorName = instructorName;
        this.lessonName = lessonName;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getScheduleTime() {
        return scheduleTime;
    }

    public void setScheduleTime(long scheduleTime) {
        this.scheduleTime = scheduleTime;
    }

    public String getStudentUid() {
        return studentUid;
    }

    public void setStudentUid(String studentUid) {
        this.studentUid = studentUid;
    }

    public String getInstructorUid() {
        return instructorUid;
    }

    public void setInstructorUid(String instructorUid) {
        this.instructorUid = instructorUid;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }
}