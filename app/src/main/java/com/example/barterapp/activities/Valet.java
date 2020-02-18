package com.example.barterapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.barterapp.R;

public class Valet extends AppCompatActivity {
    private ImageView ivPayedAmount,ivPayedCoins,ivReceivedAmount,ivReceivedCoins;
    private TextView tvPayedAmount,tvReceivedAmount;
    private int delay = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_valet);
        ivPayedAmount= findViewById(R.id.iv_payed_amount);
        ivPayedCoins= findViewById(R.id.iv_payed_coins);
        ivReceivedAmount= findViewById(R.id.iv_receiver_amount);
        ivReceivedCoins= findViewById(R.id.iv_received_coins);
        tvPayedAmount= findViewById(R.id.tv_payed_amount);
        tvReceivedAmount= findViewById(R.id.tv_received_amount);


        ivPayedAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivPayedCoins.setVisibility(View.GONE);
                tvPayedAmount.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ivPayedCoins.setVisibility(View.VISIBLE);
                        tvPayedAmount.setVisibility(View.GONE);
                    }
                },delay);
            }
        });
        ivReceivedAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivReceivedCoins.setVisibility(View.GONE);
                tvReceivedAmount.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ivReceivedCoins.setVisibility(View.VISIBLE);
                        tvReceivedAmount.setVisibility(View.GONE);
                    }
                },delay);
            }
        });
    }
}
