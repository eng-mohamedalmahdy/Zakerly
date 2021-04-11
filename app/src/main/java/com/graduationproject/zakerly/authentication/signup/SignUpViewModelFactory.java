package com.graduationproject.zakerly.authentication.signup;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;


public class SignUpViewModelFactory implements ViewModelProvider.Factory {
    private SignUpRepository repository;
    private Context context;

    public SignUpViewModelFactory(SignUpRepository repository,Context context) {
        this.repository = repository;
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SignUpViewModel.class)) {
            return (T) new SignUpViewModel(repository,context);
        }
        throw new IllegalArgumentException("Unable to construct viewmodel");
    }
}
