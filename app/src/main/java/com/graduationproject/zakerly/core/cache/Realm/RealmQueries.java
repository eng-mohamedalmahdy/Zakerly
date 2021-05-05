package com.graduationproject.zakerly.core.cache.Realm;


import com.graduationproject.zakerly.core.models.Student;
import com.graduationproject.zakerly.core.models.Instructor;
import com.graduationproject.zakerly.core.models.User;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class RealmQueries {

    Realm realm = Realm.getDefaultInstance();

    public void addStudent(Student student) {    // add Student to Realm
        realm.beginTransaction();
        Student s = realm.createObject(Student.class);
        realm.copyToRealm(student.getUser());
        s.setUser(realm.copyToRealm(student.getUser()));
        realm.commitTransaction();
    }

    public void addTeacher(Instructor teacher) {
        // add Teacher to Realm
        realm.beginTransaction();
        Instructor instructor = realm.createObject(Instructor.class);
        instructor.setUser(realm.copyToRealm(teacher.getUser()));
        instructor.setPricePerHour(teacher.getPricePerHour());
        instructor.setRate(teacher.getRate());
        realm.copyToRealm(teacher.getUser());
        realm.commitTransaction();
    }

    public User getUser(String uid) {
        return realm.where(User.class).equalTo("UID", uid).findFirst();
    }


    public void editStudent(Student student) {  //Edit Student details
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(student);
        realm.commitTransaction();
    }

    public void editTeacher(Instructor teacher) {  //Edit Teacher details
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(teacher);
        realm.commitTransaction();
    }


    public void deleteStudent(Student student) {  // Delete Student from Realm
        realm.beginTransaction();
        student.deleteFromRealm();
        realm.commitTransaction();
    }

    public void deleteTeacher(Instructor teacher) {   // Delete Teacher from Realm
        realm.beginTransaction();
        teacher.deleteFromRealm();
        realm.commitTransaction();
    }


}
