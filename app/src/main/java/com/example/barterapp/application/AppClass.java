package com.example.barterapp.application;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.example.barterapp.others.Preferences;
import com.google.firebase.FirebaseApp;
import com.onesignal.OSPermissionSubscriptionState;
import com.onesignal.OneSignal;
import com.stripe.android.PaymentConfiguration;

public class AppClass extends Application {
    Preferences preferences;
    String device_token;
    @Override
    public void onCreate() {
        super.onCreate();
        preferences = new Preferences(this);
        FirebaseApp.initializeApp(this);
// OneSignal Initialization
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

        OneSignal.idsAvailable(new OneSignal.IdsAvailableHandler() {
            @Override
            public void idsAvailable(String userId, String registrationId) {
                Log.d("debug", "User:" + userId);
                device_token = userId;
                preferences.setDeviceToken(device_token);
                if (registrationId != null)
                    Log.d("debug", "registrationId:" + registrationId);

                /*if (appPreference.getFarmerToken() != null) {
                    updateDeviceToken();
                }*/
            }
        });

        PaymentConfiguration.init(
                getApplicationContext(),
                "pk_test_TYooMQauvdEDq54NiTphI7jx"
        );
    }
}
