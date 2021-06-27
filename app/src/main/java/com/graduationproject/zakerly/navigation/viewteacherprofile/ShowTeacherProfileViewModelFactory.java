package com.graduationproject.zakerly.navigation.viewteacherprofile;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ShowTeacherProfileViewModelFactory implements ViewModelProvider.Factory {
    private ShowTeacherProfileRepository repository;

    public ShowTeacherProfileViewModelFactory(ShowTeacherProfileRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ShowTeacherProfileViewModel.class)){
            return (T) new ShowTeacherProfileViewModel();

        }
        throw new IllegalArgumentException("Unable to construct viewmodel");
    }
}
