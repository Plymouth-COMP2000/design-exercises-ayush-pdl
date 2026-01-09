package com.example.restaurant;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private static final String PREF_NAME = "restaurant_session";
    private static final String KEY_LOGGED_IN = "logged_in";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_USERTYPE = "usertype";

    private final SharedPreferences prefs;

    public SessionManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void saveLogin(String username, String usertype) {
        prefs.edit()
                .putBoolean(KEY_LOGGED_IN, true)
                .putString(KEY_USERNAME, username)
                .putString(KEY_USERTYPE, usertype)
                .apply();
    }

    public boolean isLoggedIn() {
        return prefs.getBoolean(KEY_LOGGED_IN, false);
    }

    public String getUsername() {
        return prefs.getString(KEY_USERNAME, "");
    }

    public String getUsertype() {
        return prefs.getString(KEY_USERTYPE, "");
    }

    public void logout() {
        prefs.edit().clear().apply();
    }
}
