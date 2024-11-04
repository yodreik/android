package com.example.yodreik;

import android.content.Context;
import android.content.SharedPreferences;

public class Preference {

    private static final String SHARED_PREFS_NAME = "dreik_preferences";
    private static final String AUTH_TOKEN_KEY = "access_token";

    public static void SaveAccessToken(Context context, String token) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(AUTH_TOKEN_KEY, token);
        editor.apply();
    }

    public static String GetAccessToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(AUTH_TOKEN_KEY, null);
    }

    public static boolean HasAccessToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(AUTH_TOKEN_KEY, null) != null;
    }

    public static void ClearAccessToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(AUTH_TOKEN_KEY);
        editor.apply();
    }
}
