package com.visenze.datatracking;

import com.visenze.datatracking.data.DataTrackingResponse;
import com.visenze.datatracking.data.EventsBody;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIService {

    @Headers("Content-Type:application/json; charset=UTF-8")
    @POST("events")
    Call<DataTrackingResponse> postEvents(@Query("code") String code, @Body EventsBody body);
}
