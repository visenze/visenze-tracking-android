package com.visenze.datatracking;



import android.content.Context;

import com.visenze.datatracking.data.DataCollection;
import com.visenze.datatracking.utils.Utils;

import java.util.Date;
import java.util.UUID;



public class VisenzeAnalytics {

    public static final String TAG = "DataTracking";
    public static final long SESSION_TIMEOUT = 1800000; //30 min

    private static VisenzeAnalytics mInstance;


    private String uid;
    private long sid;

    private DataCollection dataCollection;



    private VisenzeAnalytics(Context context, String uid) {
        if (uid == null || uid.isEmpty()) {
            uid = generateUid(context);
        }
        initDataTracking(context, uid);
    }

    private void initDataTracking(Context context, String uid) {
        this.uid = uid;
        this.sid = getSid(context);
        dataCollection = new DataCollection(context);
    }



    public static synchronized VisenzeAnalytics getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new VisenzeAnalytics(context, null);

        }
        return mInstance;
    }

    public static synchronized VisenzeAnalytics getInstance(Context context, String uid) {
        if(mInstance == null) {
            mInstance = new VisenzeAnalytics(context, uid);
        }
        return mInstance;
    }


    public Tracker newTracker(String code, boolean isCN) {
        Tracker t = new Tracker(code,  uid, Long.toString(sid), isCN, dataCollection);
        return t;
    }


    private String generateUid(Context context) {
        String uid = Utils.getUid(context);
        if (uid == null || uid.isEmpty()) {
            uid = UUID.randomUUID().toString();
            uid = uid.replace("-", "");
            // save uid in preference.
            Utils.saveUid(context, uid);
        }
        return uid;
    }

    private long getSid(Context context) {
        long sid = Utils.getSid(context);
        Date date = new Date();
        long now = date.getTime();
        if (sid == 0 || now - sid > SESSION_TIMEOUT) {
            sid = now;
            // save sid in preference;
            Utils.saveSid(context, sid);
        }
        return sid;
    }





}