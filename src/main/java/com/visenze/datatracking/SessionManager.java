package com.visenze.datatracking;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Date;
import java.util.UUID;

public class SessionManager {

    public final static String PREFS_NAME = "visenze_prefs";
    public final static String KEY_UID = "key_uid";
    public final static String KEY_SID = "key_sid";
    public final static String KEY_SID_TIMESTAMP = "key_sid_timestamp";
    public static final long SESSION_TIMEOUT = 1800000; //30 min
    public static final long DAY_IN_MS = 86400000;

    private SharedPreferences  preference;

    private String uid;
    private String sid;
    private long sessionTimestamp;

    public SessionManager(Context context) {
        this(context, null);
    }

    public SessionManager(Context context, String uid) {
        preference = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        this.uid = (uid != null) ? uid : generateUid();
        this.sid = getStringPref(KEY_SID);
        this.sessionTimestamp = getLongPref(KEY_SID_TIMESTAMP);
    }


    public String getUid() {
        return uid;
    }

    protected String getSessionId() {
        Date date = new Date();
        long now = date.getTime();
        if (sessionTimestamp == 0 || now - sessionTimestamp > SESSION_TIMEOUT || !isSameDay(sessionTimestamp, now)) {
            sessionTimestamp = now;
            sid = UUID.randomUUID().toString();
            // save sid and timestamp in preference
            putLongPref(KEY_SID_TIMESTAMP, sessionTimestamp);
            putStringPref(KEY_SID, sid);
        } else {
            // if tracker call getSessionId() means in send means it is still active.
            // update timestamp to the now
            sessionTimestamp = now;

        }
        return sid;
    }

    private boolean isSameDay(long t1, long t2) {
        long d1 = t1 / DAY_IN_MS;
        long d2 = t2 / DAY_IN_MS;
        return d1 == d2;
    }

    private String generateUid() {
        String uid = getStringPref(KEY_UID);
        if (uid.isEmpty()) {
            uid = UUID.randomUUID().toString();
            uid = uid.replace("-", "");
            // save uid in preference.
            putStringPref(KEY_UID, uid);
         }
        return uid;
    }



    private void putStringPref(String key, String value) {
        preference.edit()
                .putString(key, value)
                .apply();
    }

    private String getStringPref(String key) {
        return preference.getString(key, "");
    }

    private void putLongPref(String key, long value) {
        preference.edit()
                .putLong(key,value)
                .apply();
    }

    private long getLongPref(String key) {
        return preference.getLong(key, 0);
    }




}
