package com.graduationproject.zakerly.authentication.signIn;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;


public class SignInViewModelFactory implements ViewModelProvider.Factory {

    private SignInRepository repository;

    public SignInViewModelFactory(SignInRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SignInViewModel.class)) {
            return (T) new SignInViewModel(repository);
        }
        throw new IllegalArgumentException("Unable to construct viewmodel");
    }
}
