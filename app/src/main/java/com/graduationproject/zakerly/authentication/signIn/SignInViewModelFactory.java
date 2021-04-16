package com.graduationproject.zakerly.authentication.signIn;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;


public class SignInViewModelFactory implements ViewModelProvider.Factory {

    private SignInRepository repository;
    private SignInFragment fragment;

    public SignInViewModelFactory(SignInRepository repository,SignInFragment fragment) {
        this.repository = repository;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SignInViewModel.class)) {
            return (T) new SignInViewModel(repository,fragment);
        }
        throw new IllegalArgumentException("Unable to construct viewmodel");
    }
}
