package com.graduationproject.zakerly.navigation.profileTeacher;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ProfileTeacherViewModelFactory implements ViewModelProvider.Factory {
    private ProfileTeacherRepository repository;

    public ProfileTeacherViewModelFactory(ProfileTeacherRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ProfileTeacherViewModel.class)){
            return (T) new ProfileTeacherViewModel(repository);

        }
        throw new IllegalArgumentException("Unable to construct viewmodel");
    }
}
