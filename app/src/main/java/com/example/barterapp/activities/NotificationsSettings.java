package com.example.barterapp.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.barterapp.R;
import com.example.barterapp.others.Preferences;
import com.example.barterapp.responses.menu.CurrentUserNotificationSettingsResponse;
import com.example.barterapp.responses.menu.UpdateNotificationSettingsResponse;
import com.example.barterapp.utils.URLs;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

public class NotificationsSettings extends AppCompatActivity implements View.OnClickListener {
    private ImageView backBtn;
    private ToggleButton tbSound, tbNotifications, tbNewOrders, tbChatSound;
    int sound_val, notifications_val, new_orders_val, chat_sound_val;
    private Button btnSave;
    private Preferences preferences;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications_settings);
        customProgressDialog(NotificationsSettings.this);
        preferences = new Preferences(this);
        backBtn = findViewById(R.id.iv_back_notifications_settings);
        backBtn.setOnClickListener(this);
        btnSave = findViewById(R.id.btnSave_settings);
        btnSave.setOnClickListener(this);
        tbSound = findViewById(R.id.tbSound);
        tbSound.setOnClickListener(this);
        tbNotifications = findViewById(R.id.tbNotifications);
        tbNotifications.setOnClickListener(this);
        tbNewOrders = findViewById(R.id.tbNewOrder);
        tbNewOrders.setOnClickListener(this);
        tbChatSound = findViewById(R.id.tbChatSound);
        tbChatSound.setOnClickListener(this);

        getCurrentNotificationSettings();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back_notifications_settings:
                finish();
                break;
            case R.id.btnSave_settings:
                saveNotificationSettings();
                break;
            case R.id.tbSound:
                if (sound_val == 0) {
                    sound_val = 1;
                } else {
                    sound_val = 0;
                }
                break;
            case R.id.tbNotifications:
                if (notifications_val == 0) {
                    notifications_val = 1;
                } else {
                    notifications_val = 0;
                }
                break;
            case R.id.tbNewOrder:
                if (new_orders_val == 0) {
                    new_orders_val = 1;
                } else {
                    new_orders_val = 0;
                }
                break;
            case R.id.tbChatSound:
                if (chat_sound_val == 0) {
                    chat_sound_val = 1;
                } else {
                    chat_sound_val = 0;
                }
                break;
        }

    }

    private void getCurrentNotificationSettings() {
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        Gson gson = new GsonBuilder().create();
        StringRequest request = new StringRequest(Request.Method.GET, URLs.current_user_notification_settings, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(NotificationsSettings.this, "response", Toast.LENGTH_SHORT).show();
                try {
                    CurrentUserNotificationSettingsResponse settingsResponse = gson.fromJson(response, CurrentUserNotificationSettingsResponse.class);
                    sound_val = settingsResponse.getNotificationSetting().getSound();
                    notifications_val = settingsResponse.getNotificationSetting().getNotification();
                    new_orders_val = settingsResponse.getNotificationSetting().getNewOrder();
                    chat_sound_val = settingsResponse.getNotificationSetting().getChat();
                    if (sound_val == 1) {
                        tbSound.setChecked(true);
                    } else {
                        tbSound.setChecked(false);
                    }
                    if (notifications_val == 1) {
                        tbNotifications.setChecked(true);
                    } else {
                        tbNotifications.setChecked(false);
                    }
                    if (new_orders_val == 1) {
                        tbNewOrders.setChecked(true);
                    } else {
                        tbNewOrders.setChecked(false);
                    }
                    if (chat_sound_val == 1) {
                        tbChatSound.setChecked(true);
                    } else {
                        tbChatSound.setChecked(false);
                    }
                    progressDialog.dismiss();
                } catch (Exception e) {
                    Log.e("Notify sett", e.toString());
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(NotificationsSettings.this, "error", Toast.LENGTH_SHORT).show();
                Log.e("Settings_Error", error.toString());
                progressDialog.dismiss();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headerMap = new HashMap<>();
                String token = preferences.getToken();
                headerMap.put("Authorization", "Bearer " + token);
                return headerMap;

            }
        };
        requestQueue.add(request);
    }

    private void saveNotificationSettings() {
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        Gson gson = new GsonBuilder().create();
        StringRequest request = new StringRequest(Request.Method.POST, URLs.update_notification_settings_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                UpdateNotificationSettingsResponse settingsResponse = gson.fromJson(response, UpdateNotificationSettingsResponse.class);
                Toast.makeText(NotificationsSettings.this, "" + settingsResponse.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());
                progressDialog.dismiss();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headerMap = new HashMap<>();
                String token = preferences.getToken();
                headerMap.put("Authorization", "Bearer " + token);
                return headerMap;

            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("sound", String.valueOf(sound_val));
                map.put("notification", String.valueOf(notifications_val));
                map.put("new_order", String.valueOf(new_orders_val));
                map.put("chat", String.valueOf(chat_sound_val));
                return map;
            }
        };
        requestQueue.add(request);
    }

    public void customProgressDialog(Context context) {
        progressDialog = new ProgressDialog(context);
        // Setting Message
        progressDialog.setMessage("Loading...");
        // Progress Dialog Style Spinner
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // Fetching max value
        progressDialog.getMax();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
