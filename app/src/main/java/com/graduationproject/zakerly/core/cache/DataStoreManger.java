package com.graduationproject.zakerly.core.cache;

import android.content.Context;

import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.rxjava3.RxPreferenceDataStoreBuilder;
import androidx.datastore.rxjava3.RxDataStore;

import com.graduationproject.zakerly.core.constants.StoreConstants;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;


public class DataStoreManger {
    private final RxDataStore<Preferences> dataStore;
    private static DataStoreManger instance;

    public static DataStoreManger getInstance(Context context) {
        if (instance == null) instance = new DataStoreManger(context);
        return instance;
    }

    private DataStoreManger(Context context) {
        dataStore =
                new RxPreferenceDataStoreBuilder(context, /*name=*/ "app_data_store").build();

    }

    public Flowable<Boolean> getIsFirstLaunch() {
        return dataStore.data().map(prefs -> {
            Boolean res = prefs.get(StoreConstants.IS_FIRST_LAUNCH);
            return res == null ? true : res;
        });

    }

    public Flowable<Boolean> getIsDarkModeEnabled() {
        return dataStore.data().map(prefs -> {
            Boolean res = prefs.get(StoreConstants.IS_DARK_MODE_ENABLED);
            return res == null ? false : res;
        });
    }


    public Single<Preferences> setIsFirstLaunch(boolean isFirstLaunch) {
        return dataStore.updateDataAsync(prefsIn -> {
            MutablePreferences mutablePreferences = prefsIn.toMutablePreferences();
            mutablePreferences.set(StoreConstants.IS_FIRST_LAUNCH, isFirstLaunch);
            return Single.just(mutablePreferences);
        });
    }

    public Single<Preferences> setIsDarkModeEnabled(boolean enabled) {
        return dataStore.updateDataAsync(prefsIn -> {
            MutablePreferences mutablePreferences = prefsIn.toMutablePreferences();
            mutablePreferences.set(StoreConstants.IS_DARK_MODE_ENABLED, enabled);
            return Single.just(mutablePreferences);
        });
    }

}
