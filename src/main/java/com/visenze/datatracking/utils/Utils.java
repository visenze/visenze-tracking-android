package com.visenze.datatracking.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.annotation.StringRes;

import com.visenze.datatracking.R;

public class Utils {


    public static void saveUid(Context context, String uid) {
        putStringPref(context, R.string.key_uid, uid);
    }

    public static String getUid(Context context) {
        return getStringPref(context, R.string.key_uid, "");
    }

    public static void saveSid(Context context, long sid) {
        putLongPref(context, R.string.key_sid, sid);
    }

    public static Long getSid(Context context) {
        return getLongPref(context, R.string.key_sid, 0);
    }

    public static void putStringPref(Context context, int prefKeyId, String value) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(context.getString(prefKeyId), value)
                .apply();
    }

    public static String getStringPref(Context context, @StringRes int prefKeyId, String defaultValue) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String prefKey = context.getString(prefKeyId);
        return sharedPreferences.getString(prefKey, defaultValue);
    }

    public static void putLongPref(Context context, int prefKeyId, long value) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putLong(context.getString(prefKeyId), value)
                .apply();
    }

    public static long getLongPref(Context context, @StringRes int prefKeyId, long defaultValue) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String prefKey = context.getString(prefKeyId);
        return sharedPreferences.getLong(prefKey, defaultValue);
    }
}
