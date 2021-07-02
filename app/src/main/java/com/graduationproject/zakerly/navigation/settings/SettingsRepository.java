package com.graduationproject.zakerly.navigation.settings;

import android.content.Context;
import android.util.Log;

import androidx.appcompat.app.AppCompatDelegate;

import com.akexorcist.localizationactivity.ui.LocalizationActivity;
import com.graduationproject.zakerly.core.cache.DataStoreManger;
import com.graduationproject.zakerly.core.network.FacebookClient;
import com.graduationproject.zakerly.core.network.GoogleClient;
import com.graduationproject.zakerly.core.network.firebase.FireBaseAuthenticationClient;

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
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((preferences) -> setNightMode(enabled),
                        throwable -> Log.d("SETT_REPO", "setDarkModeEnabled: " + throwable.getStackTrace()));

    }

    public Flowable<Boolean> getDarkModeEnabled() {
        return DataStoreManger.getInstance(context).getIsDarkModeEnabled();
    }

    private void setNightMode(boolean enabled) {
        int mode = enabled ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO;
        AppCompatDelegate.setDefaultNightMode(mode);

    }


    public void signOut() {
        GoogleClient.getInstance().signOut();
        FacebookClient.getInstance().signOut();
        FireBaseAuthenticationClient.getInstance().signOut();
    }
}
