package com.graduationproject.zakerly.authentication.profile;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ProfileStudentViewModelFactory implements ViewModelProvider.Factory {

    private ProfileStudentRepository repository;


    public ProfileStudentViewModelFactory(ProfileStudentRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ProfileStudentViewModel.class)){
            return (T) new ProfileStudentViewModel(repository);

        }
        throw new IllegalArgumentException("Unable to construct viewmodel");
    }
}
