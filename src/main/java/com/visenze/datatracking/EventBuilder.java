package com.visenze.datatracking;

import com.visenze.datatracking.data.Event;

public class EventBuilder {


    public static Event createSearchEvent(String queryId) {
        Event event = new Event();
        event.setAction(Constants.Action.SEARCH);
        event.setQueryId(queryId);
        return event;
    }


    public static Event createClickEvent() {
        Event event = new Event();
        event.setAction(Constants.Action.CLICK);
        return event;
    }

    public static Event createViewEvent() {
        Event event = new Event();
        event.setAction(Constants.Action.VIEW);
        return event;
    }

    public static Event createProductClickEvent(String queryId, String pid, String imgUrl, int pos) {
        Event event = createClickEvent();
        event.setQueryId(queryId);
        event.setPid(pid);
        event.setImageUrl(imgUrl);
        event.setPosition(Integer.toString(pos));
        return event;
    }

    public static Event createProductImpressionEvent(String queryId, String pid, String imgUrl, int pos) {
        Event event = createViewEvent();
        event.setQueryId(queryId);
        event.setPid(pid);
        event.setImageUrl(imgUrl);
        event.setPosition(Integer.toString(pos));
        return event;
    }

    public static Event createAddCartEvent(String queryId, String pid, String imgUrl, int pos) {
        Event event = new Event();
        event.setAction(Constants.Action.ADD_TO_CART);
        event.setQueryId(queryId);
        event.setPid(pid);
        event.setImageUrl(imgUrl);
        event.setPosition(Integer.toString(pos));
        return event;
    }


    public static Event createTransactionEvent(String queryId, String transactionId, double value) {
        Event event = new Event();
        event.setAction(Constants.Action.TRANSACTION);
        event.setQueryId(queryId);
        event.setTransactionId(transactionId);
        event.setValue(Double.toString(value));
        return event;
    }

}
