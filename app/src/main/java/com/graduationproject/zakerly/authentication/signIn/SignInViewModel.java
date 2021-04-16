package com.graduationproject.zakerly.authentication.signIn;


import android.app.Activity;
import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.firebase.auth.FirebaseUser;
import com.graduationproject.zakerly.R;
import com.graduationproject.zakerly.core.constants.UserTypes;
import com.graduationproject.zakerly.core.network.FacebookClient;
import com.graduationproject.zakerly.core.network.GoogleClient;

import es.dmoral.toasty.Toasty;


public class SignInViewModel extends ViewModel {
    private SignInRepository repository;
    private MutableLiveData<FirebaseUser> userLiveData;
    private NavController controller;


    public SignInViewModel(SignInRepository repository, SignInFragment fragment) {
        this.repository = repository;
        userLiveData = repository.getUserLiveData();
        controller = NavHostFragment.findNavController(fragment);
    }


    public void signIn(String email, String password, Context context) {
        repository.signIn(email, password).addOnCompleteListener(task -> {
            if ((task.isSuccessful())) {
                Toasty.success(context, context.getText(R.string.user_signin_success)).show();
                this.userLiveData.setValue(task.getResult().getUser());
            } else {
                Toasty.error(context, context.getText(R.string.login_failure)).show();
            }
        });
    }

    public MutableLiveData<FirebaseUser> getUserLiveData() {
        return userLiveData;
    }


    public void navigateToForgetPassword() {
        controller.navigate(SignInFragmentDirections.actionSignInFragmentToForgetPasswordFragment());
    }

    public void navigateToSignUp() {
        controller.navigate(SignInFragmentDirections.actionSignInFragmentToSignUpFragment());
    }

    public void signInWithGoogle(Activity activity) {


    }

    public void signInWithFacebook(Activity activity) {
    }
}
