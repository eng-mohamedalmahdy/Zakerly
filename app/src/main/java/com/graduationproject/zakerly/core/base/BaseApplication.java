package com.graduationproject.zakerly.core.base;


import com.akexorcist.localizationactivity.ui.LocalizationApplication;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class BaseApplication extends LocalizationApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

    }

    @NotNull
    @Override
    public Locale getDefaultLanguage() {
        return Locale.ENGLISH;
    }
}
