package com.visenze.datatracking;



import android.content.Context;

import androidx.annotation.NonNull;

import com.visenze.datatracking.data.DataCollection;



public class VisenzeAnalytics {

    public static final String TAG = "DataTracking";

    private static VisenzeAnalytics mInstance;

    private DataCollection dataCollection;
    private SessionManager sessionManager;

    private VisenzeAnalytics(Context context, String uid) {
        dataCollection = new DataCollection(context);
        sessionManager = new SessionManager(context, uid);
    }

    public static synchronized VisenzeAnalytics getInstance(Context context) {
        return getInstance(context, null);
    }

    public static synchronized VisenzeAnalytics getInstance(Context context, String uid) {
        if(mInstance == null) {
            mInstance = new VisenzeAnalytics(context, uid);
        }
        return mInstance;
    }


    public Tracker newTracker(@NonNull  String code, boolean isCN) {
        Tracker t = new Tracker(code, isCN, sessionManager, dataCollection);
        return t;
    }

    public DataCollection getDataCollection() {
        return dataCollection;
    }

    public SessionManager getSessionManager() {
        return sessionManager;
    }
}
