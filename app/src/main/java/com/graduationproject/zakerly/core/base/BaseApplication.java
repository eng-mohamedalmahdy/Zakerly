package com.graduationproject.zakerly.core.base;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import androidx.annotation.NonNull;

import com.akexorcist.localizationactivity.core.LocalizationApplicationDelegate;

import java.util.Locale;

public class BaseApplication extends Application {
    private LocalizationApplicationDelegate localizationDelegate = new LocalizationApplicationDelegate();

    @Override
    protected void attachBaseContext(Context base) {
        localizationDelegate.setDefaultLanguage(base, Locale.ENGLISH);
        super.attachBaseContext(base);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        localizationDelegate.onConfigurationChanged(this);
    }

    @Override
    public Context getApplicationContext() {
        return localizationDelegate.getApplicationContext(super.getApplicationContext());
    }
}
