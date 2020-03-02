package com.example.barterapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.barterapp.R;
import com.example.barterapp.fragments.dialog_fragments.DialogFragmentRating;
import com.example.barterapp.others.Preferences;

public class SellServicesDetails extends AppCompatActivity {

    private ImageView backBtn;
    private Button completeBtn;
    private Preferences preferences;
    private String status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_details);

        preferences = new Preferences(this);
        status = preferences.getFragmentStatus();
        completeBtn = findViewById(R.id.complete_order);
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

        completeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragmentRating dialogFragmentRating = new DialogFragmentRating();
                dialogFragmentRating.show(getSupportFragmentManager(),"Rating");
            }
        });
    }
}
