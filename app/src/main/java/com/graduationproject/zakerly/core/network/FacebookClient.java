package com.graduationproject.zakerly.core.network;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.concurrent.Executor;

public class FacebookClient {
    private static FacebookClient instance;
    private static final String TAG = "FBAUTH";
    private CallbackManager callbackManager;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private FacebookClient() {

    }

    public static FacebookClient getInstance() {
        if (instance == null) instance = new FacebookClient();
        return instance;
    }

    public Task<AuthResult> handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        return mAuth.signInWithCredential(credential);
    }

    public void signOut() {
        LoginManager.getInstance().logOut();
    }
}
