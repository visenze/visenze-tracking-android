package com.visenze.datatracking;



import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.visenze.datatracking.data.DataTrackingResponse;
import com.visenze.datatracking.data.DeviceData;
import com.visenze.datatracking.data.EventData;
import com.visenze.datatracking.data.EventsBody;
import com.visenze.datatracking.data.ResultData;
import com.visenze.datatracking.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataTracking {

    public static final String TAG = "DataTracking";
    public static final long SESSION_TIMEOUT = 1800000; //30 min

    private static DataTracking mInstance;

    private APIService apiService;
    private String code;
    private String uid;
    private long sid;


    private DataCollection dataCollection;
    private DeviceData mDeviceData;

    private DataTracking(boolean isCN) {
        if (isCN) {
            apiService = HttpInstance.getRetrofitInstance(Constants.BASE_URL_CN).create(APIService.class);
        } else {
            apiService = HttpInstance.getRetrofitInstance(Constants.BASE_URL).create(APIService.class);
        }
    }


    public static synchronized DataTracking getInstance() {
        if (mInstance == null) {
            mInstance = new DataTracking(false);

        }
        return mInstance;
    }

    public static synchronized DataTracking getCNInstance() {
        if (mInstance == null) {
            mInstance = new DataTracking(true);
        }
        return mInstance;
    }


    public void init(Context context, String code) {
        String uid = getUid(context);
        this.code = code;
        initDataTracking(context, uid);
    }

    public void init(Context context, String code, String uid) {
        if (uid == null || uid.isEmpty()) {
            uid = getUid(context);
        }
        this.code = code;
        initDataTracking(context, uid);
    }


    private String getUid(Context context) {
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


    private void initDataTracking(Context context, String uid) {
        this.uid = uid;
        this.sid = getSid(context);
        dataCollection = new DataCollection(context);
    }

    private boolean isValidResponse(Response<DataTrackingResponse> response) {
        return (response.isSuccessful() && response.body() != null && "OK".equals(response.body().getStatus()));
    }

    public void setDeviceData(DeviceData deviceData) {
        mDeviceData = deviceData;
    }

    public void sendEvent(EventData e) {
        Map<String, String> map = e.toMap();

        Call<Void> eventCall = apiService.sendEvent(code, Constants.SDK_NAME, BuildConfig.VERSION_NAME, map);
        eventCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    try {
                        String body = response.errorBody().string();
                        Gson gson = new Gson();
                        DataTrackingResponse resp = gson.fromJson(body, DataTrackingResponse.class);
                        Log.d(TAG, "event send failed: " + resp.getError().getMessage());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d(TAG, "call failed");
            }
        });
    }

    public void sendEvents(List<EventData> events) {
        if (code == null) {
            Log.d(TAG, "please call init first.");
            return;
        }

        if (events.size() > 0) {
            EventsBody body = new EventsBody(uid);
            body.addEvents(events);

            Call<DataTrackingResponse> eventCall = apiService.postEvents(code, body);
            eventCall.enqueue(new Callback<DataTrackingResponse>() {
                @Override
                public void onResponse(Call<DataTrackingResponse> call, Response<DataTrackingResponse> response) {
                    if (isValidResponse(response)) {
                        // remove all events.
                        List<ResultData> results = response.body().getResult();
                        for (int i = 0; i < results.size(); i++) {
                            ResultData result = results.get(i);
                            if (result.getCode() != 0) {
                                Log.d(TAG, String.format("event %d send failed, msg: %s", i, result.getMessage()));
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<DataTrackingResponse> call, Throwable t) {
                    Log.d(TAG, "call failed");
                }
            });
        }
    }


    private EventData createEvent() {
        EventData event = dataCollection.createEvent();
        event.setUid(uid);
        event.setSessionId(Long.toString(sid));
        if (mDeviceData != null) {
            event.setAaid(mDeviceData.getAaid());
            event.setDidmd5(mDeviceData.getDidmd5());
            event.setGeo(mDeviceData.getGeo());
        }
        return event;
    }

    public EventData createSearchEvent(String queryId) {
        EventData event = createEvent();
        event.setAction(Constants.Action.SEARCH);
        event.setQueryId(queryId);
        return event;
    }


    public EventData createClickEvent() {
        EventData event = createEvent();
        event.setAction(Constants.Action.CLICK);
        return event;
    }

    public EventData createViewEvent() {
        EventData event = createEvent();
        event.setAction(Constants.Action.VIEW);
        return event;
    }

    public EventData createProductClickEvent(String queryId, String pid, String imgUrl, int pos) {
        EventData event = createClickEvent();
        event.setQueryId(queryId);
        event.setPid(pid);
        event.setImageUrl(imgUrl);
        event.setPosition(Integer.toString(pos));
        return event;
    }

    public EventData createProductImpressionEvent(String queryId, String pid, String imgUrl, int pos) {
        EventData event = createViewEvent();
        event.setQueryId(queryId);
        event.setPid(pid);
        event.setImageUrl(imgUrl);
        event.setPosition(Integer.toString(pos));
        return event;
    }

    public EventData createAddCartEvent(String queryId, String pid, String imgUrl, int pos) {
        EventData event = createEvent();
        event.setAction(Constants.Action.ADD_TO_CART);
        event.setQueryId(queryId);
        event.setPid(pid);
        event.setImageUrl(imgUrl);
        event.setPosition(Integer.toString(pos));
        return event;
    }


    public EventData createTransactionEvent(String queryId, String transactionId, double value) {
        EventData event = createEvent();
        event.setAction(Constants.Action.TRANSACTION);
        event.setQueryId(queryId);
        event.setTransactionId(transactionId);
        event.setValue(Double.toString(value));
        return event;
    }




}
