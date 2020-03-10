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

    public String getOfferId() {
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

    public void setLocation(String location) {
        editor.putString("location", location);
        editor.commit();
        editor.apply();
    }

    public String getLocation() {
        return sharedPreferences.getString("location", "");
    }

    public void setSearchVal(boolean val) {
        editor.putBoolean("search", val);
        editor.commit();
        editor.apply();
    }

    public boolean getSearchVal() {
        return sharedPreferences.getBoolean("search", false);
    }

}
