package org.grameenfoundation.search.settings;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import org.grameenfoundation.search.ApplicationRegistry;
import org.grameenfoundation.search.R;

/**
 * handles settings related operations i.e. storage and retrieval of settings values.
 */
public final class SettingsManager {
    private static final SettingsManager INSTANCE = new SettingsManager();
    private SharedPreferences sharedPreferences;

    /**
     * private constructor to avoid instantiation of this class
     */
    private SettingsManager() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ApplicationRegistry.getApplicationContext());
    }

    public static SettingsManager getInstance() {
        return INSTANCE;
    }

    /**
     * gets the settings value for the given settings key.
     *
     * @param settingKey the key for which the value is required.
     * @return the value of the settings key or null if the value cannot be found.
     */
    public String getValue(String settingKey) {
        return sharedPreferences.getString(settingKey, null);
    }

    /**
     * gets the settings value for the given settings key
     *
     * @param settingKey
     * @param defaultValue
     * @return
     */
    public String getValue(String settingKey, String defaultValue) {
        return sharedPreferences.getString(settingKey, defaultValue);
    }

    /**
     * stores a value in the settings/preferences store with
     * the given key and value
     *
     * @param settingKey
     * @param value
     */
    public void setValue(String settingKey, String value) {
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putString(settingKey, value);
        editor.commit();
    }

    /**
     * sets the default application settings.
     *
     * @param readAgain
     */
    public void setDefaultSettings(boolean readAgain) {
        PreferenceManager.setDefaultValues(ApplicationRegistry.getApplicationContext(),
                R.xml.connection_preferences, readAgain);
    }
}

