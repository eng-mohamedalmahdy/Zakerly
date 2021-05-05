package com.graduationproject.zakerly.authentication.signIn;


import android.app.Activity;
import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.firebase.auth.FirebaseUser;
import com.graduationproject.zakerly.MainActivity;
import com.graduationproject.zakerly.R;
import com.graduationproject.zakerly.core.constants.AuthTypes;
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


    public void signIn(String email, String password, Fragment fragment) {
        repository.signIn(email, password,fragment);
    }

    public MutableLiveData<FirebaseUser> getUserLiveData() {
        return userLiveData;
    }


    public void navigateToForgetPassword() {
        controller.navigate(SignInFragmentDirections.actionSignInFragmentToForgetPasswordFragment());
    }

    public void navigateToSignUp() {
        controller.navigate(SignInFragmentDirections.actionSignInFragmentToSignUpFragment(AuthTypes.AUTH_EMAIL, null, null, null, null));
    }

    public void signInWithGoogle(MainActivity activity) {

        repository.signInWithGoogle(activity);

    }

    public void signInWithFacebook(MainActivity activity) {
        repository.signInWithFacebook(activity);
    }

    public void signOut() {
        repository.signOut();
    }
}
