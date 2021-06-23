package com.graduationproject.zakerly.authentication.signup;

import android.content.Context;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavHost;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.graduationproject.zakerly.R;
import com.graduationproject.zakerly.core.cache.Realm.RealmQueries;
import com.graduationproject.zakerly.core.constants.AuthTypes;
import com.graduationproject.zakerly.core.models.Instructor;
import com.graduationproject.zakerly.core.models.Student;
import com.graduationproject.zakerly.core.network.GoogleClient;
import com.graduationproject.zakerly.core.network.firebase.FireBaseAuthenticationClient;
import com.graduationproject.zakerly.core.network.firebase.FirebaseDataBaseClient;

import es.dmoral.toasty.Toasty;

public class SignUpRepository {
    public static final String TAG = "ZAKERLY_SIGN_UP";
    private GoogleClient client;

    public SignUpRepository() {
        client = GoogleClient.getInstance();
    }

    public void signUp(Student student, String password, Fragment fragment) {

        switch (student.getUser().getAuthType()) {
            case AuthTypes.AUTH_EMAIL:
                FireBaseAuthenticationClient.getInstance().signUp(student.getUser().getEmail(), password).
                        addOnSuccessListener(authResult -> {
                            String uid = authResult.getUser().getUid();
                            student.getUser().setUID(uid);
                            FirebaseDataBaseClient.getInstance().addStudent(student);
                            RealmQueries queries = new RealmQueries();
                            queries.addStudent(student);
                            Toasty.success(fragment.getContext(), fragment.getText(R.string.user_created)).show();
                            NavHostFragment.findNavController(fragment).navigate(R.id.action_signUpFragment_to_student_app_navigation);

                        })
                        .addOnFailureListener(e -> Toasty.info(fragment.getContext(), e.getLocalizedMessage()).show());


                break;
            case AuthTypes.AUTH_G_MAIL:
                client.firebaseAuthWithGoogle(student.getUser().getUID()).
                        addOnSuccessListener(authResult -> {
                            String uid = authResult.getUser().getUid();
                            student.getUser().setUID(uid);
                            FirebaseDataBaseClient.getInstance().addStudent(student);
                            RealmQueries queries = new RealmQueries();
                            queries.addStudent(student);
                            Toasty.success(fragment.getContext(), fragment.getText(R.string.user_created)).show();
                            NavHostFragment.findNavController(fragment).navigate(R.id.action_signUpFragment_to_student_app_navigation);
                            Log.d(TAG, "signUp: Student added" + student);
                        })
                        .addOnFailureListener(e -> Toasty.info(fragment.getContext(), e.getLocalizedMessage()).show());
                break;

            case AuthTypes.AUTH_FACEBOOK:
                FirebaseDataBaseClient.getInstance().addStudent(student).addOnSuccessListener(aVoid ->
                {
                    RealmQueries queries = new RealmQueries();
                    queries.addStudent(student);
                    Toasty.success(fragment.getContext(), fragment.getText(R.string.user_created)).show();
                    NavHostFragment.findNavController(fragment).navigate(R.id.action_signUpFragment_to_student_app_navigation);
                    Log.d(TAG, "signUp: Student added" + student);

                })
                        .addOnFailureListener(e -> Toasty.info(fragment.getContext(), e.getLocalizedMessage()).show());
                break;
        }
    }

    public void signUp(Instructor instructor, String password, Fragment fragment) {
        switch (instructor.getUser().getAuthType()) {
            case AuthTypes.AUTH_EMAIL:
                FireBaseAuthenticationClient.getInstance().signUp(instructor.getUser().getEmail(), password).
                        addOnSuccessListener(authResult -> {
                            Log.d(TAG, authResult.toString());
                            String uid = authResult.getUser().getUid();
                            instructor.getUser().setUID(uid);
                            FirebaseDataBaseClient.getInstance().addInstructor(instructor);
                            RealmQueries queries = new RealmQueries();
                            queries.addTeacher(instructor);
                            Toasty.success(fragment.getContext(), fragment.getText(R.string.user_created)).show();
                        })
                        .addOnFailureListener(e -> Toasty.info(fragment.getContext(), e.getLocalizedMessage()).show());
                break;

            case AuthTypes.AUTH_G_MAIL:
                client.firebaseAuthWithGoogle(instructor.getUser().getUID()).
                        addOnSuccessListener(authResult -> {
                            String uid = authResult.getUser().getUid();
                            instructor.getUser().setUID(uid);
                            FirebaseDataBaseClient.getInstance().addInstructor(instructor);
                            RealmQueries queries = new RealmQueries();
                            queries.addTeacher(instructor);
                            Toasty.success(fragment.getContext(), fragment.getText(R.string.user_created)).show();
                        })
                        .addOnFailureListener(e -> Toasty.info(fragment.getContext(), e.getLocalizedMessage()).show());
                break;

            case AuthTypes.AUTH_FACEBOOK:
                FirebaseDataBaseClient.getInstance().addInstructor(instructor).addOnSuccessListener(aVoid -> {
                    Toasty.success(fragment.getContext(), fragment.getText(R.string.user_created)).show();
                    RealmQueries queries = new RealmQueries();
                    queries.addTeacher(instructor);
                }).addOnFailureListener(e -> Toasty.info(fragment.getContext(), e.getLocalizedMessage()).show());
                break;
        }
    }

    public Task<DataSnapshot> getSpecialisationsList() {
        return FirebaseDataBaseClient.getInstance().getSpecialisations();
    }
}
