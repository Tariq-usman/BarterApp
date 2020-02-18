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
    public void setFragmentStatus(String fragmentStatus){
        editor.putString("notification_fragment", fragmentStatus);
        editor.apply();
        editor.commit();
    }
    public String getFragmentStatus(){
        return sharedPreferences.getString("notification_fragment","");
    }
}
