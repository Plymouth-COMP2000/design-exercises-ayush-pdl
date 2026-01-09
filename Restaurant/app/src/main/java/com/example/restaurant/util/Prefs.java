package com.example.restaurant.util;

import android.content.Context;
import android.content.SharedPreferences;

public class Prefs {

    private static final String PREF_NAME = "restaurant_prefs";

    public static final String KEY_NEW_BOOKING = "new_booking";
    public static final String KEY_CANCELLED = "cancelled_booking";
    public static final String KEY_MENU = "menu_change";

    private static SharedPreferences prefs(Context c) {
        return c.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static void save(Context c, String key, boolean value) {
        prefs(c).edit().putBoolean(key, value).apply();
    }

    public static boolean load(Context c, String key) {
        return prefs(c).getBoolean(key, true);
    }
}
