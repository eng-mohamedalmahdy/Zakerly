package com.graduationproject.zakerly.authentication.signup;

import android.content.Context;
import android.util.Log;

import com.graduationproject.zakerly.R;
import com.graduationproject.zakerly.core.models.Instructor;
import com.graduationproject.zakerly.core.models.Student;
import com.graduationproject.zakerly.core.network.firebase.FireBaseAuthenticationClient;
import com.graduationproject.zakerly.core.network.firebase.FirebaseDataBaseClient;

import es.dmoral.toasty.Toasty;

public class SignUpRepository {
    public static final String TAG = "ZAKERLY_SIGN_UP";

    public void signUp(Student student, String password, Context context) {
        FireBaseAuthenticationClient.getInstance().signUp(student.getUser().getEmail(), password).
                addOnSuccessListener(authResult -> {
                    String uid = authResult.getUser().getUid();
                    student.getUser().setUID(uid);
                    FirebaseDataBaseClient.getInstance().addStudent(student);
                    Toasty.success(context, context.getText(R.string.user_created)).show();
                })
                .addOnFailureListener(e -> Toasty.info(context, e.getLocalizedMessage()).show());

    }

    public void signUp(Instructor instructor, String password, Context context) {
        FireBaseAuthenticationClient.getInstance().signUp(instructor.getUser().getEmail(), password).
                addOnSuccessListener(authResult -> {
                    Log.d(TAG, authResult.toString());
                    String uid = authResult.getUser().getUid();
                    instructor.getUser().setUID(uid);
                    FirebaseDataBaseClient.getInstance().addInstructor(instructor);
                    Toasty.success(context, context.getText(R.string.user_created)).show();
                })
                .addOnFailureListener(e -> Toasty.info(context, e.getLocalizedMessage()).show());
    }
}
