package com.graduationproject.zakerly.core.base;

import com.akexorcist.localizationactivity.ui.LocalizationActivity;
import com.graduationproject.zakerly.settings.SettingsRepository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class BaseActivity extends LocalizationActivity {
    private SettingsRepository settingsRepository;

    @Override
    protected void onStart() {
        super.onStart();
        settingsRepository = new SettingsRepository(this);
        setDarkMode();

    }

    private void setDarkMode() {
        settingsRepository.getDarkModeEnabled().
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.single())
                .subscribe(darkModeEnabled -> {
                    settingsRepository.setDarkModeEnabled(darkModeEnabled);
                });
    }


}
