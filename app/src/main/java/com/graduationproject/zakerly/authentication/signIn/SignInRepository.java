package com.graduationproject.zakerly.authentication.signIn;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.graduationproject.zakerly.R;
import com.graduationproject.zakerly.core.network.firebase.FireBaseAuthenticationClient;

import es.dmoral.toasty.Toasty;


public class SignInRepository {


    private MutableLiveData<FirebaseUser> userLiveData;

    public SignInRepository() {
        this.userLiveData = new MutableLiveData<>();

        if (FireBaseAuthenticationClient.getInstance().getCurrentUser() != null){
            userLiveData.postValue(FireBaseAuthenticationClient.getInstance().getCurrentUser());

        }
    }
    public Task<AuthResult> signIn(String email , String password){
    return     FireBaseAuthenticationClient.getInstance().signIn(email,password);

    }


    public MutableLiveData<FirebaseUser> getUserLiveData(){
        return userLiveData;
    }

}
