package com.graduationproject.zakerly.core.constants;

import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.core.PreferencesKeys;

public class StoreConstants {
    public static final Preferences.Key<Boolean> IS_FIRST_LAUNCH = PreferencesKeys.booleanKey("IS_FIRST_LAUNCH");
    public static final Preferences.Key<Boolean> IS_DARK_MODE_ENABLED = PreferencesKeys.booleanKey("IS_DARK_MODE_ENABLED");
    public static final Preferences.Key<String> LANGUAGE = PreferencesKeys.stringKey("LANGUAGE");

}
