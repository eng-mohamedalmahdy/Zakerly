package com.graduationproject.zakerly.network;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.graduationproject.zakerly.core.models.Student;

public class FirebaseDataBaseClient {

    private static FirebaseDataBaseClient instance;
    private static FirebaseDatabase database;
    private static DatabaseReference usersReference;


    private FirebaseDataBaseClient() {
        database = FirebaseDatabase.getInstance();
        usersReference = database.getReference("users");
    }

    public static FirebaseDataBaseClient getInstance() {
        if (instance == null) instance = new FirebaseDataBaseClient();
        return instance;
    }

    public void addStudent(Student student) {
        usersReference.child(student.getUser().getUID()).setValue(student);
    }

}
