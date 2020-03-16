package com.example.barterapp.application;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.barterapp.R;
import com.example.barterapp.others.Preferences;
import com.example.barterapp.responses.user.UpdateDeviceTokenResponse;
import com.example.barterapp.utils.URLs;
import com.google.firebase.FirebaseApp;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.onesignal.OSPermissionSubscriptionState;
import com.onesignal.OneSignal;
import com.stripe.android.PaymentConfiguration;

import java.util.HashMap;
import java.util.Map;

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

                if (preferences.getToken() != null) {
                    updateDeviceToken();
                }
            }
        });

        PaymentConfiguration.init(
                getApplicationContext(),
                getString(R.string.stripe_api_key)
        );
    }

    private void updateDeviceToken() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        Gson gson = new GsonBuilder().create();
        StringRequest request = new StringRequest(Request.Method.POST, URLs.update_device_token_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AppClass.this, "Error", Toast.LENGTH_SHORT).show();
                Log.e("App class", error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headerMap = new HashMap<>();
                headerMap.put("Authorization", "Bearer " + preferences.getToken());
                return headerMap;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("device_token", device_token);
                return map;
            }
        };
        requestQueue.add(request);
    }
}
