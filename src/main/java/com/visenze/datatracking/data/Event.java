package com.visenze.datatracking.data;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.visenze.datatracking.Constants;


import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

public class Event {

    public String getCategory() {
        return category;
    }

    public String getAction() {
        return action;
    }

    public String getName() {
        return name;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public Double getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }

    public String getQueryId() {
        return queryId;
    }

    public String getFromReqId() {
        return fromReqId;
    }

    public String getUid() {
        return uid;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getSource() {
        return source;
    }

    public String getVersion() {
        return version;
    }

    public String getSdk() {
        return sdk;
    }

    public String getPid() {
        return pid;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Integer getPosition() {
        return position;
    }

    public String getBrand() {
        return brand;
    }

    public Double getPrice() {
        return price;
    }

    public String getCurrency() {
        return currency;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getUrl() {
        return url;
    }

    public String getReferrer() {
        return referrer;
    }

    public String getPlatform() {
        return platform;
    }

    public String getOs() {
        return os;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public String getScreenResolution() {
        return screenResolution;
    }

    public String getAppId() {
        return appId;
    }

    public String getAppName() {
        return appName;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public String getDeviceBrand() {
        return deviceBrand;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public String getAaid() {
        return aaid;
    }

    public String getDidmd5() {
        return didmd5;
    }

    public String getGeo() {
        return geo;
    }

    public String getLang() {
        return lang;
    }

    public String getCarrier() {
        return carrier;
    }

    public String getCampaign() {
        return campaign;
    }

    public String getCampaignAdGroup() {
        return campaignAdGroup;
    }

    public String getCampaignCreative() {
        return campaignCreative;
    }

    public String getS1() {
        return s1;
    }

    public String getS2() {
        return s2;
    }

    public String getS3() {
        return s3;
    }

    public String getS4() {
        return s4;
    }

    public String getS5() {
        return s5;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setQueryId(String queryId) {
        this.queryId = queryId;
    }

    public void setFromReqId(String fromReqId) {
        this.fromReqId = fromReqId;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setSdk(String sdk) {
        this.sdk = sdk;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setReferrer(String referrer) {
        this.referrer = referrer;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public void setScreenResolution(String screenResolution) {
        this.screenResolution = screenResolution;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public void setDeviceBrand(String deviceBrand) {
        this.deviceBrand = deviceBrand;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public void setAaid(String aaid) {
        this.aaid = aaid;
    }

    public void setDidmd5(String didmd5) {
        this.didmd5 = didmd5;
    }

    public void setGeo(String geo) {
        this.geo = geo;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public void setCampaign(String campaign) {
        this.campaign = campaign;
    }

    public void setCampaignAdGroup(String campaignAdGroup) {
        this.campaignAdGroup = campaignAdGroup;
    }

    public void setCampaignCreative(String campaignCreative) {
        this.campaignCreative = campaignCreative;
    }

    public void setS1(String s1) {
        this.s1 = s1;
    }

    public void setS2(String s2) {
        this.s2 = s2;
    }

    public void setS3(String s3) {
        this.s3 = s3;
    }

    public void setS4(String s4) {
        this.s4 = s4;
    }

    public void setS5(String s5) {
        this.s5 = s5;
    }

    @SerializedName("cat")
    @Expose
    private String category;

    @SerializedName("action")
    @Expose
    private String action;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("ts")
    @Expose
    private long timestamp;

    @SerializedName("value")
    @Expose
    private Double value;

    @SerializedName("label")
    @Expose
    private String label;

    @SerializedName("queryId")
    @Expose
    private String queryId;

    @SerializedName("fromReqId")
    @Expose
    private String fromReqId;

    @SerializedName("uid")
    @Expose
    private String uid;

    @SerializedName("sid")
    @Expose
    private String sessionId;

    @SerializedName("source")
    @Expose
    private String source;

    @SerializedName("v")
    @Expose
    private String version;

    @SerializedName("sdk")
    @Expose
    private String sdk;

    @SerializedName("pid")
    @Expose
    private String pid;

    @SerializedName("imUrl")
    @Expose
    private String imageUrl;

    @SerializedName("pos")
    @Expose
    private Integer position;

    @SerializedName("brand")
    @Expose
    private String brand;

    @SerializedName("price")
    @Expose
    private Double price;

    @SerializedName("currency")
    @Expose
    private String currency;

    @SerializedName("productUrl")
    @Expose
    private String productUrl;

    @SerializedName("transId")
    @Expose
    private String transactionId;

    @SerializedName("url")
    @Expose
    private String url;

    @SerializedName("r")
    @Expose
    private String referrer;

    @SerializedName("p")
    @Expose
    private String platform;

    @SerializedName("os")
    @Expose
    private String os;

    @SerializedName("osv")
    @Expose
    private String osVersion;

    @SerializedName("sr")
    @Expose
    private String screenResolution;

    @SerializedName("ab")
    @Expose
    private String appId;

    @SerializedName("an")
    @Expose
    private String appName;

    @SerializedName("av")
    @Expose
    private String appVersion;

    @SerializedName("db")
    @Expose
    private String deviceBrand;

    @SerializedName("dm")
    @Expose
    private String deviceModel;

    @SerializedName("aaid")
    @Expose
    private String aaid;

    @SerializedName("didmd5")
    @Expose
    private String didmd5;

    @SerializedName("geo")
    @Expose
    private String geo;

    @SerializedName("lang")
    @Expose
    private String lang;

    @SerializedName("carrier")
    @Expose
    private String carrier;

    @SerializedName("c")
    @Expose
    private String campaign;

    @SerializedName("cag")
    @Expose
    private String campaignAdGroup;

    @SerializedName("cc")
    @Expose
    private String campaignCreative;

    @Expose
    private String s1;

    @Expose
    private String s2;

    @Expose
    private String s3;

    @Expose
    private String s4;

    @Expose
    private String s5;

    @Expose
    private BigDecimal n1;

    @Expose
    private BigDecimal n2;

    @Expose
    private BigDecimal n3;

    @Expose
    private BigDecimal n4;

    @Expose
    private BigDecimal n5;


    public BigDecimal getN1() {
        return n1;
    }

    public BigDecimal getN2() {
        return n2;
    }

    public BigDecimal getN3() {
        return n3;
    }

    public BigDecimal getN4() {
        return n4;
    }

    public BigDecimal getN5() {
        return n5;
    }


    public void setN1(BigDecimal n1) {
        this.n1 = n1;
    }

    public void setN2(BigDecimal n2) {
        this.n2 = n2;
    }

    public void setN3(BigDecimal n3) {
        this.n3 = n3;
    }

    public void setN4(BigDecimal n4) {
        this.n4 = n4;
    }

    public void setN5(BigDecimal n5) {
        this.n5 = n5;
    }




    protected Event() {
        Date date = new Date();
        timestamp = date.getTime();
    }

    public Map<String, String> toMap() {
        Gson gson = new Gson();
        String jStr = gson.toJson(this);
        return gson.fromJson(jStr, Map.class);
    }

    public static boolean isValidEvent(Event e) {
        String action = e.getAction();
        if(action == null) {
            return false;
        }
        if (action.equals(Constants.Action.SEARCH)) {
            return (e.queryId != null);
        }

        if (action.equals(Constants.Action.PRODUCT_CLICK) || action.equals(Constants.Action.PRODUCT_VIEW) || action.equals(Constants.Action.ADD_TO_CART)) {
            return (e.queryId != null && e.pid != null && e.imageUrl != null);
        }

        if (action.equals(Constants.Action.TRANSACTION)) {
            return (e.queryId != null && e.transactionId != null);
        }
        return true;
    }


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
        Event event = new Event();
        event.setAction(Constants.Action.PRODUCT_CLICK);
        event.setQueryId(queryId);
        event.setPid(pid);
        event.setImageUrl(imgUrl);
        event.setPosition(pos);
        return event;
    }

    public static Event createProductImpressionEvent(String queryId, String pid, String imgUrl, int pos) {
        Event event = new Event();
        event.setAction(Constants.Action.PRODUCT_VIEW);
        event.setQueryId(queryId);
        event.setPid(pid);
        event.setImageUrl(imgUrl);
        event.setPosition(pos);
        return event;
    }

    public static Event createAddCartEvent(String queryId, String pid, String imgUrl, int pos) {
        Event event = new Event();
        event.setAction(Constants.Action.ADD_TO_CART);
        event.setQueryId(queryId);
        event.setPid(pid);
        event.setImageUrl(imgUrl);
        event.setPosition(pos);
        return event;
    }


    public static Event createTransactionEvent(String queryId, String transactionId, double value) {
        Event event = new Event();
        event.setAction(Constants.Action.TRANSACTION);
        event.setQueryId(queryId);
        event.setTransactionId(transactionId);
        event.setValue(value);
        return event;
    }




}
