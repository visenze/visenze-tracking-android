package com.visenze.datatracking;

import com.visenze.datatracking.data.DataTrackingResponse;
import com.visenze.datatracking.data.EventsBody;
import com.visenze.datatracking.retry.Retry;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface APIService {

    @Headers("Content-Type:application/json; charset=UTF-8")
    @POST("events")
    Call<DataTrackingResponse> postEvents(@Query("code") String code, @Body EventsBody body);

    @Retry
    @Headers("Content-Type:application/json; charset=UTF-8")
    @GET("__va.gif")
    Call<Void> sendEvent(@Query("code") String code, @Query("sdk") String sdk, @Query("v") String sdkVersion, @QueryMap Map<String, String> params);
}
