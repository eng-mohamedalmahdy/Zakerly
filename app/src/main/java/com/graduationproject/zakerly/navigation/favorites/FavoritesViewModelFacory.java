package com.graduationproject.zakerly.navigation.favorites;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.graduationproject.zakerly.navigation.profilestudent.ProfileStudentViewModel;

public class FavoritesViewModelFacory implements ViewModelProvider.Factory {

    private FavoriteRepoistory repoistory;

    public FavoritesViewModelFacory(FavoriteRepoistory repoistory) {
        this.repoistory = repoistory;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ProfileStudentViewModel.class)){
            return (T) new FavoritesViewModel(repoistory);

        }
        throw new IllegalArgumentException("Unable to construct viewmodel");
    }
}
