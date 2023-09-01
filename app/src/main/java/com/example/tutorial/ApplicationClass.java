package com.example.tutorial;

import android.app.Application;

import com.onesignal.OneSignal;

public class ApplicationClass extends Application {
    private final String ONESIGNAL_APP_ID = "79045777-5704-4935-a619-9eaf29e7f419";
    @Override
    public void onCreate() {
        super.onCreate();
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);
        OneSignal.promptForPushNotifications();
    }
}
