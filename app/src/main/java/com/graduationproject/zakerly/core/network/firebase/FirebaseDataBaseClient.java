package com.graduationproject.zakerly.core.network.firebase;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.graduationproject.zakerly.core.constants.UserTypes;
import com.graduationproject.zakerly.core.models.Instructor;
import com.graduationproject.zakerly.core.models.Student;

import java.util.function.Function;


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

    public Query getUser(String email) {
        return usersReference.orderByChild("user/email").equalTo(email);
    }

    public void doWithUserObject(String email,
                                 Function<Student, Boolean> studentAction,
                                 Function<Instructor, Boolean> instructorAction, Function<String, Boolean> errorAction) {
        FirebaseDataBaseClient.getInstance().getUser(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String type = "";
                for (DataSnapshot child : snapshot.getChildren()) {
                    type = child.child("user/type").getValue(String.class);
                    if (UserTypes.TYPE_INSTRUCTOR.equals(type)) {
                        Instructor instructor = child.getValue(Instructor.class);
                        instructorAction.apply(instructor);
                    } else if (UserTypes.TYPE_STUDENT.equals(type)) {
                        Student student = child.getValue(Student.class);
                        studentAction.apply(student);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                errorAction.apply(error.getMessage());
            }
        });
    }
}
