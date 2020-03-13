package com.example.barterapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.barterapp.R;

public class ChoosePaymentMethodTwo extends AppCompatActivity {
    private static final int REQUEST_CODE_LOCATION = 1;
    private ImageView backBtn, ivMasterCard, ivVisa;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_payment_method_two);

        ivMasterCard = findViewById(R.id.master_card);
        ivVisa = findViewById(R.id.visa);
        backBtn = findViewById(R.id.iv_back_pay_method_two);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ivMasterCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ChoosePaymentMethodThree.class));
            }
        });
        ivVisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
