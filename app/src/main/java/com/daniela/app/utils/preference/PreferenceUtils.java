package com.daniela.app.utils.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.daniela.app.ui.app.App;
import com.daniela.data.managers.remote.StringUtils;


public class PreferenceUtils {


    private static final String PREF_USER_TOKEN = "PREF_USER_TOKEN";
    private static final String PREF_DEVICE_TOKEN = "PREF_DEVICE_TOKEN";
    private static final String PREF_USER_ROL = "PREF_USER_ROL";

    public static void clearPreferences() {
        getPreferences(App.getGlobalContext()).edit().clear().apply();
    }

    PreferenceUtils() {
        throw new IllegalStateException("Utility class, please don't instantiate");
    }

    protected static SharedPreferences getPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    protected static SharedPreferences.Editor getEditor(Context context) {
        return getPreferences(context).edit();
    }

    public static boolean isUserLogged() {
        return !StringUtils.isEmpty(getUserRol());
    }

    public static String getUserToken() {
        return getPreferences(App.getGlobalContext()).getString(PREF_USER_TOKEN, "");
    }

    public static void setUserToken(String token) {
        getEditor(App.getGlobalContext()).putString(PREF_USER_TOKEN, token).apply();
    }

    public static String getUserRol() {
        return getPreferences(App.getGlobalContext()).getString(PREF_USER_ROL, "");
    }


}
