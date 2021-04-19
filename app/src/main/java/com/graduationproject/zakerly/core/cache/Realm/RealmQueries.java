package com.graduationproject.zakerly.core.cache.Realm;


import com.graduationproject.zakerly.core.models.Student;
import com.graduationproject.zakerly.core.models.Instructor;

import io.realm.Realm;

public class RealmQueries {

    Realm realm = Realm.getDefaultInstance();

    public void addStudent(Student student){    // add Student to Realm
        realm.beginTransaction();
        realm.createObject(Student.class);
        realm.commitTransaction();
    }

    public void addTeacher(Instructor teacher){  // add Teacher to Realm
        realm.beginTransaction();
        realm.createObject(Student.class);
        realm.commitTransaction();
    }


    public void editStudent(Student student){  //Edit Student details
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(student);
        realm.commitTransaction();
    }

    public void editTeacher(Instructor teacher){  //Edit Teacher details
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(teacher);
        realm.commitTransaction();
    }


    public void deleteStudent(Student student){  // Delete Student from Realm
        realm.beginTransaction();
        student.deleteFromRealm();
        realm.commitTransaction();
   }

    public void deleteTeacher(Instructor teacher){   // Delete Teacher from Realm
        realm.beginTransaction();
        teacher.deleteFromRealm();
        realm.commitTransaction();
    }

}
