package com.graduationproject.zakerly.navigation.favorites;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class FavoritesViewModelFacory implements ViewModelProvider.Factory {

    private FavoriteRepository repoistory;

    public FavoritesViewModelFacory(FavoriteRepository repoistory) {
        this.repoistory = repoistory;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(FavoritesViewModel.class)){
            return (T) new FavoritesViewModel(repoistory);

        }
        throw new IllegalArgumentException("Unable to construct viewmodel");
    }
}
