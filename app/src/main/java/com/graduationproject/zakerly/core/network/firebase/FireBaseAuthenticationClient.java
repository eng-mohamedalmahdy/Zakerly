package com.graduationproject.zakerly.core.network.firebase;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FireBaseAuthenticationClient {

    private static FireBaseAuthenticationClient instance;
    private static FirebaseAuth authInstance;

    public static FireBaseAuthenticationClient getInstance() {
        if (instance == null) {
            instance = new FireBaseAuthenticationClient();
        }
        return instance;
    }

    private FireBaseAuthenticationClient() {
        authInstance = FirebaseAuth.getInstance();
    }

    public Task<AuthResult> signUp(String email, String password) {
        return authInstance.createUserWithEmailAndPassword(email, password);
    }



    public Task<AuthResult> signIn(String email, String password) {
        return authInstance.signInWithEmailAndPassword(email, password);
    }

    public Task<Void> forgetPassword(String email) {
        return authInstance.sendPasswordResetEmail(email);
    }

    public FirebaseUser getCurrentUser() {
        return authInstance.getCurrentUser();
    }

    public void signOut() {
        authInstance.signOut();
    }



}
