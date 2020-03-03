package com.example.barterapp.others;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    Context context;
    public Preferences(Context context){
        this.context=context;
        sharedPreferences= context.getSharedPreferences("com.example.barterapp",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setToken(String token) {
        editor.putString("token", token);
        editor.apply();
        editor.commit();
    }

    public String getToken() {
        return sharedPreferences.getString("token", "");
    }



    public void setFragmentStatus(String fragmentStatus){
        editor.putString("notification_fragment", fragmentStatus);
        editor.apply();
        editor.commit();
    }
    public String getFragmentStatus(){
        return sharedPreferences.getString("notification_fragment","");
    }

    public void setEditStatus(int status){
        editor.putInt("edit_profile",status);
        editor.apply();
        editor.commit();
    }
    public int getEditStatus(){
        return sharedPreferences.getInt("edit_profile",0);
    }

    public void setLocation(String location){
        editor.putString("location",location);
        editor.commit();
        editor.apply();
    }
    public String getLocation(){
        return sharedPreferences.getString("location","");
    }

    public void setSearchVal(boolean val){
        editor.putBoolean("location",val);
        editor.commit();
        editor.apply();
    }
    public boolean getSearchVal(){
        return sharedPreferences.getBoolean("location",false);
    }
}
