package com.graduationproject.zakerly.userutils.editprofile;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.graduationproject.zakerly.navigation.favorites.FavoritesViewModel;

public class EditProfileViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(EditProfileViewModel.class)) {
            return (T) new EditProfileViewModel();
        }
        throw new IllegalArgumentException("Unable to construct viewmodel");
    }
}
