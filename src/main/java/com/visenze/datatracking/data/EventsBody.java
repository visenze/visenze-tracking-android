package com.visenze.datatracking.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.visenze.datatracking.BuildConfig;
import com.visenze.datatracking.Constants;

import java.util.ArrayList;
import java.util.List;

public class EventsBody {


    @SerializedName("v")
    @Expose
    private String v;

    @SerializedName("sdk")
    @Expose
    private String sdk;

    @SerializedName("uid")
    @Expose
    private String uid;

    @SerializedName("events")
    @Expose
    private List<Event> events;


    public EventsBody(String uid) {
        this.uid = uid;
        this.v = BuildConfig.VERSION_NAME;
        this.sdk = Constants.SDK_NAME;
        events = new ArrayList<Event>();
    }

    public void addEvents(List<Event> data) {
        events.addAll(data);
    }

    public void addEvent(Event e) {
        events.add(e);
    }

}
