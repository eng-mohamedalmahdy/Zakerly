package com.graduationproject.zakerly.navigation.instructorAccountPage;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class InstructorPageViewModelFactory implements ViewModelProvider.Factory {
    private InstructorPageRepository repository;

    public InstructorPageViewModelFactory(InstructorPageRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(InstructorPageViewModel.class)){
            return (T) new InstructorPageViewModel();

        }
        throw new IllegalArgumentException("Unable to construct viewmodel");
    }
}
