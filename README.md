# visenze-tracking-android

Android SDK for Visenze data tracking


## 1. Overview

Visenze data tracking sdk is an open source software to send user defined data tracking event to visenze for performance monitoring and analytic purpose.

## 2. Setup and nitialization

To initialize sdk, call init() method with application or activity context and a request code as parameter.

```java

DataTracking.getInstance().init(this, code);

```

please contact visenze to get a code to uniquely identify your application.

You can also set a uid to data tracking sdk. If the uid is not set. By default, the sdk will generate unique id to identify the customer.
in addition, you can also pass device information to sdk to help visenze better understand the customer and improve analytics.

```java
DataTracking.getInstance().init(this, code, "YOUR UID");

DeviceData d = new DeviceData();
d.setAaid("Google Advertising ID");
d.setDidmd5("Hashed device IMEI");
d.setGeo("1.3521, 103.8198"); // geo info in Lat and Long, seperated by comma
DataTracking.getInstance().setDeviceData(d);
```
d.setAaid()


## 3. Send events

After SDK has been initialized, use addEvent() to add events to the request queue.

```
EventData e1 = DataTracking.getInstance().addEvent();
e1.setAction(Constants.Action.SEARCH)

EventData e2 = DataTracking.getInstance().addEvent();
e2.setAction(Constants.Action.CLICK);
e2.setQueryId("YOUR QUERY ID IN SEARCH API");

DataTracking.getInstance().sendEvents();
```

addEvent() will add a new event in a queue to be send later and return the current event instance. Note that Event action is a mandatory field.
Finally, sendEvents() will send out all events currently in the queue.



