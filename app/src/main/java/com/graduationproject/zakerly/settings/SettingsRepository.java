package com.graduationproject.zakerly.settings;

import android.app.UiModeManager;
import android.content.Context;
import android.os.Build;

import androidx.appcompat.app.AppCompatDelegate;

import com.akexorcist.localizationactivity.ui.LocalizationActivity;
import com.graduationproject.zakerly.core.cache.DataStoreManger;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SettingsRepository {
    private final Context context;

    public SettingsRepository(Context context) {
        this.context = context;
    }

    public void setDarkModeEnabled(boolean enabled) {
        DataStoreManger.getInstance(context).setIsDarkModeEnabled(enabled)
                .subscribeOn(Schedulers.single())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((preferences, throwable) -> setNightMode(enabled));

    }

    public Flowable<Boolean> getDarkModeEnabled() {
        return DataStoreManger.getInstance(context).getIsDarkModeEnabled();
    }

    public void setLanguage(String language, LocalizationActivity activity) {

        DataStoreManger.getInstance(context).setLanguage(language)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.single())
                .subscribe();
        activity.setLanguage(language);

    }

    public Flowable<String> getLanguage(){
        return DataStoreManger.getInstance(context).getLanguage();
    }

    private void setNightMode(boolean enabled) {
        int mode = enabled ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO;
        AppCompatDelegate.setDefaultNightMode(mode);
    }


}
