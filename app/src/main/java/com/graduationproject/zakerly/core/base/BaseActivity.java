package com.graduationproject.zakerly.core.base;

import com.akexorcist.localizationactivity.ui.LocalizationActivity;
import com.graduationproject.zakerly.navigation.settings.SettingsRepository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class BaseActivity extends LocalizationActivity {
    private SettingsRepository settingsRepository;
    private CompositeDisposable disposables;
    @Override
    protected void onStart() {
        super.onStart();
        settingsRepository = new SettingsRepository(this);
        disposables = new CompositeDisposable();
        setDarkMode();

    }


    private void setDarkMode() {
      Disposable darkModeDisposable = settingsRepository.getDarkModeEnabled().
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.single())
                .subscribe(darkModeEnabled -> {
                    settingsRepository.setDarkModeEnabled(darkModeEnabled);
                });

      disposables.add(darkModeDisposable);
    }

    @Override
    protected void onPause() {
        super.onPause();
        disposables.dispose();
    }

    @Override
    protected void onStop() {
        super.onStop();
        disposables.dispose();
    }
}


