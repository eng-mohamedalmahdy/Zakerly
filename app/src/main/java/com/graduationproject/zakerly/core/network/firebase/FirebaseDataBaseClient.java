package com.graduationproject.zakerly.core.network.firebase;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.graduationproject.zakerly.core.models.Instructor;
import com.graduationproject.zakerly.core.models.Student;


public class FirebaseDataBaseClient {

    private static FirebaseDataBaseClient instance;
    private static FirebaseDatabase database;
    private static DatabaseReference usersReference;
    private static DatabaseReference specialisationsReference;


    private FirebaseDataBaseClient() {
        database = FirebaseDatabase.getInstance();
        usersReference = database.getReference("users");
        specialisationsReference = database.getReference("specialisations");
    }

    public static FirebaseDataBaseClient getInstance() {
        if (instance == null) instance = new FirebaseDataBaseClient();
        return instance;
    }

    public Task<Void> addStudent(Student student) {
        return usersReference.child(student.getUser().getUID()).setValue(student);
    }

    public Task<Void> addInstructor(Instructor instructor) {
        return usersReference.child(instructor.getUser().getUID()).setValue(instructor);
    }

    public Task<DataSnapshot> getSpecialisations() {
        return specialisationsReference.get();
    }
}
