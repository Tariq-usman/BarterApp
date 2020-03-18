package com.example.barterapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.barterapp.R;
import com.example.barterapp.fragments.dialog_fragments.DialogFragmentWallet;
import com.example.barterapp.others.Preferences;
import com.example.barterapp.responses.user.UserBalanceResponse;
import com.example.barterapp.utils.URLs;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

public class Wallet extends AppCompatActivity {
    private ImageView backBtn, addAmountToWallet, ivPayedAmount, ivPayedCoins, ivReceivedAmount, ivReceivedCoins;
    private TextView tvPayedAmount, tvReceivedAmount, tvTotalBalance;
    private int delay = 3000;
    private Preferences preferences;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        customProgressDialog(Wallet.this);
        preferences = new Preferences(this);
        getTotalBalance();
        backBtn = findViewById(R.id.iv_back_wallet);
        addAmountToWallet = findViewById(R.id.iv_add_valet);
        tvTotalBalance = findViewById(R.id.tv_total_balance);
        ivPayedAmount = findViewById(R.id.iv_payed_amount);
        ivPayedCoins = findViewById(R.id.iv_payed_coins);
        ivReceivedAmount = findViewById(R.id.iv_receiver_amount);
        ivReceivedCoins = findViewById(R.id.iv_received_coins);
        tvPayedAmount = findViewById(R.id.tv_payed_amount);
        tvReceivedAmount = findViewById(R.id.tv_received_amount);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        addAmountToWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragmentWallet dialogFragmentWallet = new DialogFragmentWallet();
                dialogFragmentWallet.show(getSupportFragmentManager(), "wallet");
            }
        });
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
                }, delay);
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
                }, delay);
            }
        });
    }

    private void getTotalBalance() {
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        Gson gson = new GsonBuilder().create();
        StringRequest request = new StringRequest(Request.Method.GET, URLs.get_total_balance_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                UserBalanceResponse balanceResponse = gson.fromJson(response, UserBalanceResponse.class);
                tvTotalBalance.setText("$ "+balanceResponse.getBalance().getWallet().getBalance().toString());
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("wallet error",error.toString());
                progressDialog.dismiss();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headerMap = new HashMap<>();
                headerMap.put("Authorization", "Bearer " + preferences.getToken());
                return headerMap;
            }
        };
        requestQueue.add(request);
    }

    public void customProgressDialog(Context context) {
        progressDialog = new ProgressDialog(context);
        // Setting Message
        progressDialog.setMessage("Loading...");
        // Progress Dialog Style Spinner
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // Fetching max value
        progressDialog.getMax();
    }

}
