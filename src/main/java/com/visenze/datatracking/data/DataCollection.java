package com.visenze.datatracking.data;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;
import android.util.Log;

import com.visenze.datatracking.Constants;
import com.visenze.datatracking.data.Event;

import java.util.Locale;

public class DataCollection {

    public static String TAG = "DataCollection";

    public String getPlatform() {
        return platform;
    }

    public String getOs() {
        return os;
    }

    public String getOsv() {
        return osv;
    }

    public String getScreenResolution() {
        return screenResolution;
    }

    public String getDeviceBrand() {
        return deviceBrand;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public String getLanguage() {
        return language;
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

    private String platform;
    private String os;
    private String osv;
    private String screenResolution;
    private String deviceBrand;
    private String deviceModel;
    private String language;


    private String appId;
    private String appName;
    private String appVersion;


    public DataCollection(Context context) {
        getSystemInfo();
        getAppInfo(context);
    }


    private void getAppInfo(Context context) {
        appId = context.getPackageName();
        try {
            final PackageManager pm = context.getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo(appId, 0);
            appName = (String) pm.getApplicationLabel(ai);

            PackageInfo pInfo = pm.getPackageInfo(appId, 0);
            appVersion = pInfo.versionName;

        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "unable to get packageInfo ");
        }
    }


    private void getSystemInfo() {
        platform = Constants.PLATFORM;
        os = Constants.OS;
        int apiVersion = android.os.Build.VERSION.SDK_INT;
        osv = Integer.toString(apiVersion);
        deviceBrand = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model != null && model.toLowerCase().startsWith(deviceBrand.toLowerCase())) {
            deviceModel = model;
        } else if (model != null) {
            deviceModel = deviceBrand + " " + model;
        }

        int width = Resources.getSystem().getDisplayMetrics().widthPixels;
        int height = Resources.getSystem().getDisplayMetrics().heightPixels;

        screenResolution = width + "x" + height;
        language = Locale.getDefault().toLanguageTag();
    }




}