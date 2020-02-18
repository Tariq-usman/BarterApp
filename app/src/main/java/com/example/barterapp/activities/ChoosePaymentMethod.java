package com.example.barterapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.barterapp.R;

public class ChoosePaymentMethod extends AppCompatActivity implements View.OnClickListener {
private Button confirmOrder;
private LinearLayout layoutRetService,layoutGetPay;
private ImageView backBtn;
private boolean selectOption;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_payment_method);
        backBtn = findViewById(R.id.iv_back_pay_method);
        backBtn.setOnClickListener(this);

        layoutRetService = findViewById(R.id.layout_re_serv);
        layoutRetService.setOnClickListener(this);
        layoutGetPay = findViewById(R.id.layout_get_pay);
        layoutGetPay.setOnClickListener(this);
        confirmOrder = findViewById(R.id.confirm_order);
        confirmOrder.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back_pay_method:
                finish();
                break;
            case R.id.layout_re_serv:
                    selectOption = true;
                    layoutRetService.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_service_details_button_clicked));
                    layoutGetPay.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_service_details_button));
                break;
            case R.id.layout_get_pay:
                selectOption=false;
                layoutGetPay.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_service_details_button_clicked));
                layoutRetService.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_service_details_button));
                break;
            case R.id.confirm_order:
                if (selectOption==true) {
                    startActivity(new Intent(ChoosePaymentMethod.this, ChooseService.class));
                }else if (selectOption==false){
                    startActivity(new Intent(ChoosePaymentMethod.this, ChoosePaymentMethodTwo.class));

                }
                break;
        }

    }
}
