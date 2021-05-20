package com.graduationproject.zakerly.navigation.settings;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.graduationproject.zakerly.authentication.signIn.SignInFragment;
import com.graduationproject.zakerly.authentication.signIn.SignInRepository;
import com.graduationproject.zakerly.authentication.signIn.SignInViewModel;

public class SettingsViewModelFactory implements ViewModelProvider.Factory {

    private SettingsRepository repository;

    public SettingsViewModelFactory(SettingsRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SettingsViewModel.class)) {
            return (T) new SettingsViewModel(repository);
        }
        throw new IllegalArgumentException("Unable to construct viewmodel");
    }
}
