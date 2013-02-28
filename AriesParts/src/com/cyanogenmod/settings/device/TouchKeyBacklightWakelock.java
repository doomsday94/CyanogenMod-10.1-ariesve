package com.cyanogenmod.settings.device;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceManager;

public class TouchKeyBacklightWakelock implements OnPreferenceChangeListener {

    private static final String FILE = "/sys/class/misc/notification/wakelock";

    public static boolean isSupported() {
        return Utils.fileExists(FILE);
    }

    /**
     * Restore backlight timeout setting from SharedPreferences. (Write to kernel.)
     * @param context       The context to read the SharedPreferences from
     */
    public static void restore(Context context) {
        if (!isSupported()) {
            return;
        }

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        Utils.writeValue(FILE, sharedPrefs.getBoolean(DeviceSettings.KEY_BACKLIGHT_WAKELOCK, false) ? "1" : "0");
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {		
	    Utils.writeValue(FILE, ((Boolean)newValue) ? "1" : "0");
        return true;
    }

}

