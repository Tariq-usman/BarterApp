package com.example.barterapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.barterapp.R;
import com.example.barterapp.others.Preferences;

public class Splash extends AppCompatActivity {
    private Preferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        preferences = new Preferences(this);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (preferences.getToken() == "") {
                    startActivity(new Intent(Splash.this, SignInActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(Splash.this, MainPage.class));
                    finish();
                }
            }
        }, 3000);
    }
}
