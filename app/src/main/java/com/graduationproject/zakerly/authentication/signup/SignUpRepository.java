package com.graduationproject.zakerly.authentication.signup;

import android.util.Log;

import com.graduationproject.zakerly.core.models.Instructor;
import com.graduationproject.zakerly.core.models.Student;
import com.graduationproject.zakerly.core.network.firebase.FireBaseAuthenticationClient;
import com.graduationproject.zakerly.core.network.firebase.FirebaseDataBaseClient;

public class SignUpRepository {
    public static final String TAG = "ZAKERLY_SIGN_UP";

    public void signUp(Student student, String password) {
        FireBaseAuthenticationClient.getInstance().signUp(student.getUser().getEmail(), password).
                addOnSuccessListener(authResult -> {
                    Log.d(TAG,authResult.getUser().getEmail());
                    String uid = authResult.getUser().getUid();
                    student.getUser().setUID(uid);
                    FirebaseDataBaseClient.getInstance().addStudent(student);
                });

    }

    public void signUp(Instructor instructor, String password) {
        FireBaseAuthenticationClient.getInstance().signUp(instructor.getUser().getEmail(), password).
                addOnSuccessListener(authResult -> {
                    Log.d(TAG,authResult.toString());
                    String uid = authResult.getUser().getUid();
                    instructor.getUser().setUID(uid);
                    FirebaseDataBaseClient.getInstance().addInstructor(instructor);
                });
    }
}
