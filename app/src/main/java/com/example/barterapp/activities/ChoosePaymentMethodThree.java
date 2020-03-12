package com.example.barterapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.barterapp.R;
import com.example.barterapp.activities.CompleteOrder;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.Stripe;
import com.stripe.android.model.Card;
import com.stripe.android.model.ConfirmPaymentIntentParams;
import com.stripe.android.model.Customer;
import com.stripe.android.model.PaymentMethodCreateParams;
import com.stripe.android.view.CardInputWidget;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ChoosePaymentMethodThree extends AppCompatActivity {
    private Button submitBtn;
    private TextView tvDay, tvYear;
    private LinearLayout layoutDate;
    private ImageView backBtn;
    private DatePickerDialog datePickerDialog;
    private int mYear, mMonth, mDay;
    CardInputWidget cardInputWidget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_payment_method_three);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        cardInputWidget = findViewById(R.id.cardInputWidget);
        tvDay = findViewById(R.id.tv_day);
        tvYear = findViewById(R.id.tv_year);
        backBtn = findViewById(R.id.iv_back_pay_method_three);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        submitBtn = findViewById(R.id.submit);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CompleteOrder.class));
            }
        });
        layoutDate = findViewById(R.id.date_layout);
        layoutDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Date
                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(ChoosePaymentMethodThree.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                tvDay.setText(dayOfMonth + "");
//                                tvDeliveyDateMonth.setText(monthOfYear + 1 + "");
                                tvYear.setText(year + "");
//                                tvDeliveyDateDay.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.setTitle("Select Date..");
                datePickerDialog.show();
            }
        });
    }
}
