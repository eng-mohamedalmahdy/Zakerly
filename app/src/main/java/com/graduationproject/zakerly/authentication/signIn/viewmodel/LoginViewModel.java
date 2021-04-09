package com.graduationproject.zakerly.authentication.signIn.viewmodel;

import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.graduationproject.zakerly.BR;
import com.graduationproject.zakerly.authentication.signIn.model.User;

public class LoginViewModel extends BaseObservable {
    private User user;
    Context context;
    FirebaseAuth mAuth;
    private String successMessage = "Login Was Successful";
    private String errorMessage = "Email Or Password not valid";

    @Bindable
    private String toastMessage = null;
    public String getToastMessage(){
        return toastMessage;
    }
    private void setToastMessage(String toastMessage){
        this.toastMessage = toastMessage;
        notifyPropertyChanged(BR.toastMessage);
    }
    @Bindable
    public String getUserEmail(){
        return user.getEmail();
    }
    public void setUserEmail(String userEmail){
        user.setEmail(userEmail);
        notifyPropertyChanged(BR.userEmail);
    }

    @Bindable
    public String getUserPassword(){
        return user.getPassword();
    }
    public void setUserPassword(String userPassword){
        user.setPassword(userPassword);
        notifyPropertyChanged(BR.userPassword);
    }
    public LoginViewModel (Context context){
        this.context = context;
        user = new User("","");
    }
    public void onLoginClicked(){
        if (isInputDataValid()){
            setToastMessage(successMessage);
            mAuth.signInWithEmailAndPassword(getUserEmail(),getUserPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(context, "login is successful", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(context, "loggin is failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }else{
            setToastMessage(errorMessage);
        }
    }

    private boolean isInputDataValid() {
        return !TextUtils.isEmpty(getUserEmail()) &&
                !TextUtils.isEmpty(getUserPassword())&&
                Patterns.EMAIL_ADDRESS.matcher(getUserEmail()).matches() &&
                getUserPassword().length() > 5
                ;
    }
}
