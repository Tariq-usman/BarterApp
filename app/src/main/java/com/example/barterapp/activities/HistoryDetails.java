package com.example.barterapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.barterapp.R;
import com.example.barterapp.others.Preferences;

public class HistoryDetails extends AppCompatActivity {

    private ImageView backBtn;
    private Button completeBtn;
    private Preferences preferences;
    private String status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_details);
        completeBtn = findViewById(R.id.complete_order);
        preferences = new Preferences(this);
        status = preferences.getFragmentStatus();
        backBtn = findViewById(R.id.iv_back_history_details);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (status.equalsIgnoreCase("sell services")){
            completeBtn.setVisibility(View.VISIBLE);
        }else if (status.equalsIgnoreCase("history")){
            completeBtn.setVisibility(View.GONE);
        }
    }
}
