package com.graduationproject.zakerly.authentication.signIn;



import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseUser;
import com.graduationproject.zakerly.core.constants.UserTypes;


public class SignInViewModel extends ViewModel {
    private SignInRepository repository ;
    private MutableLiveData<String> currentType;
    private MutableLiveData<FirebaseUser> userLiveData;


    public SignInViewModel(SignInRepository repository) {
        this.repository = repository;
        userLiveData = repository.getUserLiveData();
        currentType = new MutableLiveData(UserTypes.TYPE_INSTRUCTOR);
    }


    public void signIn(String email , String password, Context context){
        repository.signIn(email,password,context);
    }

    public MutableLiveData<FirebaseUser> getUserLiveData(){
        return userLiveData;
    }
    public MutableLiveData<String> getCurrentType() {
        return currentType;
    }
}
