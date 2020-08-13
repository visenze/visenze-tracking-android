package com.visenze.datatracking;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.visenze.datatracking.APIService;
import com.visenze.datatracking.BuildConfig;
import com.visenze.datatracking.Constants;
import com.visenze.datatracking.HttpInstance;
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
    private APIService apiService;
    private String code;
    private String uid;
    private String sid;
    private DeviceData mDeviceData;
    private DataCollection dataCollection;

    public Tracker(String code, String uid, String sid, boolean isCN, DataCollection dataCollection) {
        this.code = code;
        this.uid = uid;
        this.sid = sid;
        if (isCN) {
            apiService = HttpInstance.getRetrofitInstance(Constants.BASE_URL_CN).create(APIService.class);
        } else {
            apiService = HttpInstance.getRetrofitInstance(Constants.BASE_URL).create(APIService.class);
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
            Log.d(TAG, "please provide valid code");
            return;
        }
        if (!Event.isValidEvent(e)) {
            Log.d(TAG, "Event is not valid, check missing fields !");
        }

        addFields(e); // add additional field if not set by user.
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

    public void sendEvents(List<Event> events) {
        if (code == null) {
            Log.d(TAG, "please provide a valid code");
            return;
        }


        if (events.size() > 0) {
            EventsBody body = new EventsBody(uid);

            for(Event e : events) {
                if(Event.isValidEvent(e)) {
                    addFields(e);
                    body.addEvent(e);
                } else {
                    Log.d(TAG, "Event " + e.getAction() + " is not valid, check missing fields !");
                }
            }


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

    private void addFields(Event e) {
        if(e.getUid() == null) {
            e.setUid(uid);
        }
        if(e.getSessionId() == null) {
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


}
