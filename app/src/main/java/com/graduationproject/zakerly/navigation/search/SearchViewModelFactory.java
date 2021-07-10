package com.graduationproject.zakerly.navigation.search;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.graduationproject.zakerly.navigation.instructorAccountPage.InstructorPageViewModel;

public class SearchViewModelFactory implements ViewModelProvider.Factory {
    private SearchRepository repository;

    public SearchViewModelFactory(SearchRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(InstructorPageViewModel.class)){
            return (T) new SearchViewModel(repository);

        }
        throw new IllegalArgumentException("Unable to construct viewmodel");
    }
}
