package com.graduationproject.zakerly.navigation.settings;

import androidx.lifecycle.ViewModel;

import io.reactivex.rxjava3.core.Flowable;


public class SettingsViewModel extends ViewModel {

    private SettingsRepository repository;

    public SettingsViewModel(SettingsRepository repository) {
        this.repository = repository;
    }

    public void signOut() {
        repository.signOut();
    }

    public Flowable<Boolean> getNightModeEnable() {
        return repository.getDarkModeEnabled();
    }

    public void setNightModeEnabled(boolean enabled) {
        repository.setDarkModeEnabled(enabled);
    }
}
