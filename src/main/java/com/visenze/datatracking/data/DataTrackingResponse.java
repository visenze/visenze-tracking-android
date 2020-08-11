package com.visenze.datatracking.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataTrackingResponse {
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("reqid")
    @Expose
    private String reqid;

    @SerializedName("result")
    @Expose
    private List<ResultData> result;


    public String getStatus() {
        return status;
    }

    public String getReqid() {
        return reqid;
    }

    public List<ResultData> getResult() {
        return result;
    }



}
