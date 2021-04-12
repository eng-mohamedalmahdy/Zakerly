package com.graduationproject.zakerly.core.network;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Executor;

public class FacebookClient {
    private static final String TAG ="FBAUTH" ;
    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private static final String Email="email";
    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    public Activity activity;
    public FacebookClient (Activity activity){
        this.activity=activity;
    }

    public void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //go to the other fragment from here

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(activity, "Error while Sign in with Facebook", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
