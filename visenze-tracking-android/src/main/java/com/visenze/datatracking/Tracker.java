package com.visenze.datatracking;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.visenze.datatracking.data.DataCollection;
import com.visenze.datatracking.data.DataTrackingResponse;
import com.visenze.datatracking.data.DeviceData;
import com.visenze.datatracking.data.Event;
import com.visenze.datatracking.data.EventsBody;
import com.visenze.datatracking.data.ResultData;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Tracker {

    public static final String TAG = "Tracker";

    private TrackingService trackingService;
    private String code;
    private String uid;
    private DeviceData mDeviceData;
    private DataCollection dataCollection;
    private SessionManager sessionManager;

    public Tracker(@NonNull String code, boolean isCN, SessionManager sessionManager, DataCollection dataCollection) {
        this.code = code;
        this.sessionManager = sessionManager;
        this.uid = sessionManager.getUid();
        if (isCN) {
            trackingService = HttpInstance.getRetrofitInstance(Constants.BASE_URL_CN).create(TrackingService.class);
        } else {
            trackingService = HttpInstance.getRetrofitInstance(Constants.BASE_URL).create(TrackingService.class);
        }

        this.dataCollection = dataCollection;
    }

    private boolean isValidResponse(Response<DataTrackingResponse> response) {
        return (response.isSuccessful() && response.body() != null && "OK".equals(response.body().getStatus()));
    }

    public void setDeviceData(DeviceData deviceData) {
        mDeviceData = deviceData;
    }

    public void sendEvent(Event e) {
        if (code == null) {
            throw new RuntimeException("code must not be null");
        }

        if (!Event.isValidEvent(e)) {
            warnMissingEventFields(e);
            return;
        }

        // generate random trans id if not provided
        if (e.isTransactionEvent() && e.getTransactionId() == null) {
            e.setTransactionId(Event.generateRandomTransId());
        }

        addFields(e); // add additional field if not set by user.
        Map<String, String> map = e.toMap();

        Call<Void> eventCall = trackingService.sendEvent(code, Constants.SDK_NAME, BuildConfig.VERSION_NAME, map);
        eventCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    try {
                        String body = response.errorBody().string();
                        Gson gson = new Gson();
                        DataTrackingResponse resp = gson.fromJson(body, DataTrackingResponse.class);
                        logError(resp);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e(TAG, "call failed");
            }
        });
    }

    public void sendEvents(List<Event> events) {
        if (code == null) {
            throw new RuntimeException("code must not be null");
        }

        if (events.size() > 0) {
            EventsBody body = new EventsBody(uid);

            // for batch events, use same generated trans id if missing
            String randomTransId = Event.generateRandomTransId();

            for(Event e : events) {
                if(Event.isValidEvent(e)) {
                    addFields(e);

                    if (e.isTransactionEvent() && e.getTransactionId() == null) {
                        e.setTransactionId(randomTransId);
                    }

                    body.addEvent(e);
                } else {
                    warnMissingEventFields(e);
                }
            }

            Call<DataTrackingResponse> eventCall = trackingService.postEvents(code, body);
            eventCall.enqueue(new Callback<DataTrackingResponse>() {
                @Override
                public void onResponse(Call<DataTrackingResponse> call, Response<DataTrackingResponse> response) {
                    if (isValidResponse(response)) {
                        // remove all events.
                        List<ResultData> results = response.body().getResult();
                        for (int i = 0; i < results.size(); i++) {
                            ResultData result = results.get(i);
                            if (result.getCode() != 0) {
                                Log.e(TAG, String.format("event %d send failed, msg: %s", i, result.getMessage()));
                            }
                        }
                    } else {
                        if(response.errorBody() != null) {
                            try {
                                String body = response.errorBody().string();
                                Gson gson = new Gson();
                                DataTrackingResponse resp = gson.fromJson(body, DataTrackingResponse.class);
                                logError(resp);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<DataTrackingResponse> call, Throwable t) {
                    Log.e(TAG, "failed to send events batch");
                }
            });
        }
    }

    private void logError(DataTrackingResponse resp) {
        if (resp != null && resp.getError() != null) {
            Log.e(TAG, "event send failed: " + resp.getError().getMessage());
        }
    }

    private void warnMissingEventFields(Event event) {
        if (event == null) return;

        Log.e(TAG, "Event: " + event.toMap() + " is not valid, check missing fields !");
    }

    private void addFields(Event e) {
        if(e.getUid() == null) {
            e.setUid(uid);
        }

        if(e.getSessionId() == null) {
            String sid = sessionManager.getSessionId();
            e.setSessionId(sid);
        }

        if(mDeviceData != null) {
            if(e.getAaid() == null) {
                e.setAaid(mDeviceData.getAaid());
            }
            if(e.getDidmd5() == null) {
                e.setDidmd5(mDeviceData.getDidmd5());
            }
            if(e.getGeo() == null) {
                e.setGeo(mDeviceData.getGeo());
            }
        }

        if(dataCollection != null) {
            e.setPlatform(dataCollection.getPlatform());
            e.setOs(dataCollection.getOs());
            e.setOsVersion(dataCollection.getOsv());
            e.setScreenResolution(dataCollection.getScreenResolution());
            e.setAppId(dataCollection.getAppId());
            e.setAppName(dataCollection.getAppName());
            e.setAppVersion(dataCollection.getAppVersion());
            e.setDeviceBrand(dataCollection.getDeviceBrand());
            e.setDeviceModel(dataCollection.getDeviceModel());
            e.setLang(dataCollection.getLanguage());
        }
    }

    public String getUid() {
        return uid;
    }

    public String getSessionId() {
        return sessionManager.getSessionId();
    }
}
