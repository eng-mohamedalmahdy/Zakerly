package com.graduationproject.zakerly.core.network;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;


import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;


public class GoogleClient {

    private GoogleSignInClient mGoogleSignInClient;
    public final static int RC_SIGN_IN = 123;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static GoogleClient instance;

    private GoogleClient() {
    }

    public static GoogleClient getInstance() {
        if (instance==null)instance = new GoogleClient();
        return instance;
    }

    public void createRequest(Activity activity) {
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("399767924752-o1l7eoelpio8fcn6dvjpe070nhjfutt7.apps.googleusercontent.com")
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(activity.getApplicationContext(), gso);
    }

    public void signIn(Activity activity) {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        activity.startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public Task<AuthResult> firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        return mAuth.signInWithCredential(credential);
    }

    public void signOut(){
        mGoogleSignInClient.signOut();
    }

}
