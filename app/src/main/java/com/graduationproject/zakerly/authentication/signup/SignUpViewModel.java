package com.graduationproject.zakerly.authentication.signup;

import androidx.lifecycle.ViewModel;

public class SignUpViewModel extends ViewModel {
    private SignUpRepository repository;

    public SignUpViewModel(SignUpRepository repository) {
        this.repository = repository;
    }

}