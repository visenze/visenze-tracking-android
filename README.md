# visenze-tracking-android

Android SDK for Visenze data tracking


## 1. Overview

Visenze data tracking sdk is an open source software to send user defined data tracking event to visenze for performance monitoring and analytic purpose.

## 2. Setup and nitialization
include project dependency into gradle file

```
implementation 'com.visenze.datatracking:tracking:0.1.4'
```


To initialize sdk, extend Application and initialize the sdk with application context and request code.

In manifest set the MyApplication as application name

```
    <application
        android:name=".MyApplication"
        ...
    </application>
```


```java

public class MyApplication extends Application {

    private static VisenzeAnalytics dataTracking;
    private static Tracker tracker;
    @Override
    public void onCreate() {
        super.onCreate();

        dataTracking = VisenzeAnalytics.getInstance(this);
    }

    synchronized public Tracker getDefaultTracker() {
        if(tracker == null) {
            String code = "YOUR CODE";
            tracker = dataTracking.newTracker(code, false);
        }
        return tracker;
    }
}

```

please contact visenze to get a code to uniquely identify your application.

You can also set a uid to data tracking sdk. If uid is not set, a random (non-personalizable) string will be generated to identify the application for analytics purposes.
in addition, you can also pass device information to sdk to help visenze better understand the customer and improve analytics.

```java

    Tracker tracker = ((MyApplication) getApplication()).getDefaultTracker();
    DeviceData deviceData = new DeviceData();
    deviceData.setDidmd5("MD5 hased device ID");
    tracker.setDeviceData(deviceData);

```

## 3. Send events

To create event,  use Event.createXXXEvent() with input parameters to create event.

```
    Event e1 = Event.createViewEvent();
    Event e2 = Event.createSearchEvent("QUERY_ID");
    Event e3 = Event.createProductImpressionEvent("QUERY_ID", "PID", "IMAGE_URL", 1);
```

use tracker.sendEvent(Event e) and tracker.sendEvents(List<Event> events)

```

    Tracker t = ((MyApplication) getApplication()).getDefaultTracker();
    t.sendEvent(e1);  // send single event.

    List<Event> events = new ArrayList<Event> ();
    events.add(e2);
    events.add(e3);
    t.sendEvents(events);
```

Note that Event.createXXXEvents will create events with all necessary parameters. Invalid events missing required parameters will not be sent to server.
                                                                                  .
please search in log to see the error messages for failed events
