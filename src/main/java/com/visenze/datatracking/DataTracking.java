package com.visenze.datatracking;



import android.content.Context;
import android.util.Log;

import com.visenze.datatracking.data.DataTrackingResponse;
import com.visenze.datatracking.data.DeviceData;
import com.visenze.datatracking.data.EventData;
import com.visenze.datatracking.data.EventsBody;
import com.visenze.datatracking.data.ResultData;
import com.visenze.datatracking.utils.Utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataTracking {

    public static final String TAG = "DataTracking";
    public static final long SESSION_TIMEOUT = 900000; //15 min

    private static DataTracking mInstance;

    private APIService apiService;
    private String code;
    private String uid;
    private long sid;

    private List<EventData> events;
    private DataCollection dataCollection;
    private DeviceData mDeviceData;

    private DataTracking() {
        apiService = HttpInstance.getRetrofitInstance(Constants.BASE_URL).create(APIService.class);
        events = new ArrayList<EventData>();
    }


    public static synchronized DataTracking getInstance() {
        if(mInstance == null) {
            mInstance = new DataTracking();

        }
        return mInstance;
    }


    public void init(Context context, String code) {
        String uid = getUid(context);
        this.code = code;
        initDataTracking(context, uid);
    }

    public void init(Context context, String code, String uid) {
        if(uid == null || uid.isEmpty()) {
            uid = getUid(context);
        }
        this.code = code;
        initDataTracking(context, uid);
    }


    private String getUid(Context context) {
        String uid = Utils.getUid(context);
        if(uid == null || uid.isEmpty()) {
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
        if(sid == 0 || now - sid > SESSION_TIMEOUT) {
            sid = now;
            // save sid in preference;
            Utils.saveSid(context, sid);
        }
        return sid;
    }


    private void initDataTracking(Context context, String uid) {
        this.uid = uid;
        this.sid = getSid(context);
        dataCollection = new DataCollection(context);
    }

    private boolean isValidResponse(Response<DataTrackingResponse> response) {
        return (response.isSuccessful() && response.body() !=null && "OK".equals(response.body().getStatus()));
    }

    public void setDeviceData(DeviceData deviceData) {
        mDeviceData = deviceData;
    }

    public void sendEvents() {
        if(code == null) {
            Log.d(TAG, "please call init first.");
            return;
        }

        if(events.size() > 0) {
            EventsBody body = new EventsBody(uid);
            body.addEvents(events);

            Call<DataTrackingResponse> eventCall= apiService.postEvents(code, body);
            eventCall.enqueue(new Callback<DataTrackingResponse>() {
                @Override
                public void onResponse(Call<DataTrackingResponse> call, Response<DataTrackingResponse> response) {
                    if(isValidResponse(response)) {
                        // remove all events.
                        List<ResultData> results = response.body().getResult();
                        for(int i=0; i<results.size();i++) {
                            ResultData result = results.get(i);
                            if(result.getCode() != 0) {
                                Log.d(TAG,  String.format("event %d send failed, msg: %s", i, result.getMessage()));
                            }
                        }
                        events.clear();
                        // Log.d(TAG, "send send successful");
                    }
                }

                @Override
                public void onFailure(Call<DataTrackingResponse> call, Throwable t) {
                    Log.d(TAG, "call failed");
                }
            });
        }
    }


    public EventData addEvent() {
        EventData event = dataCollection.createEvent();
        event.setUid(uid);
        event.setSid(Long.toString(sid));
        if(mDeviceData != null) {
            event.setAaid(mDeviceData.getAaid());
            event.setDidmd5(mDeviceData.getDidmd5());
            event.setGeo(mDeviceData.getGeo());
        }
        events.add(event);
        return event;
    }






}
