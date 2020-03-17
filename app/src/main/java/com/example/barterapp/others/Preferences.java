package com.example.barterapp.others;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    Context context;

    public Preferences(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("com.example.barterapp", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setDeviceToken(String deviceToken) {
        editor.putString("device_token", deviceToken);
        editor.apply();
        editor.commit();
    }

    public String getDeviceToken() {
        return sharedPreferences.getString("device_token", "");
    }

    public void setToken(String token) {
        editor.putString("token", token);
        editor.apply();
        editor.commit();
    }

    public String getToken() {
        return sharedPreferences.getString("token", "");
    }

    public void setOfferId(String offerId) {
        editor.putString("offerId", offerId);
        editor.apply();
        editor.commit();
    }

    public String   getOfferId() {
        return sharedPreferences.getString("offerId", "");
    }

    public void setUserId(int user_id) {
        editor.putInt("user_id", user_id);
        editor.apply();
        editor.commit();
    }

    public int getUserId() {
        return sharedPreferences.getInt("user_id", 0);
    }

    public void setOrderId(int order_id) {
        editor.putInt("order_id", order_id);
        editor.apply();
        editor.commit();
    }

    public int getOrderId() {
        return sharedPreferences.getInt("order_id", 0);
    }


    public void setJobUserId(int job_user_id) {
        editor.putInt("job_user_id", job_user_id);
        editor.apply();
        editor.commit();
    }

    public int getJobUserId() {
        return sharedPreferences.getInt("job_user_id", 0);
    }

    public void setSenderId(int sender_id) {
        editor.putInt("sender_id", sender_id);
        editor.apply();
        editor.commit();
    }

    public int getSenderId() {
        return sharedPreferences.getInt("sender_id", 0);
    }


    public void setActorId(int job_user_id) {
        editor.putInt("actor_id", job_user_id);
        editor.apply();
        editor.commit();
    }

    public int getActorId() {
        return sharedPreferences.getInt("actor_id", 0);
    }

    public void setFragmentStatus(String fragmentStatus) {
        editor.putString("notification_fragment", fragmentStatus);
        editor.apply();
        editor.commit();
    }

    public String getFragmentStatus() {
        return sharedPreferences.getString("notification_fragment", "");
    }

    public void setEditStatus(int status) {
        editor.putInt("edit_profile", status);
        editor.apply();
        editor.commit();
    }

    public int getEditStatus() {
        return sharedPreferences.getInt("edit_profile", 0);
    }
    public void setLocationStatus(String location_status) {
        editor.putString("location_status", location_status);
        editor.commit();
        editor.apply();
    }
    public String getLocationStatus() {
        return sharedPreferences.getString("location_status", "");
    }
    public void setUserLocation(String location) {
        editor.putString("user location", location);
        editor.commit();
        editor.apply();
    }

    public String getUserLocation() {
        return sharedPreferences.getString("user location", "");
    }

    public void setSearchVal(boolean val) {
        editor.putBoolean("search", val);
        editor.commit();
        editor.apply();
    }

    public boolean getSearchVal() {
        return sharedPreferences.getBoolean("search", false);
    }

    public void setTaskLocation(String add_task) {
        editor.putString("add_task", add_task);
        editor.commit();
        editor.apply();
    }
    public String getTaskLocation() {
        return sharedPreferences.getString("add_task", "");
    }
}
