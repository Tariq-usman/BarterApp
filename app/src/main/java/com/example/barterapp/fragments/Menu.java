package com.example.barterapp.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.barterapp.activities.History;
import com.example.barterapp.R;
import com.example.barterapp.activities.SignInActivity;
import com.example.barterapp.activities.TradesHistory;
import com.example.barterapp.activities.Wallet;
import com.example.barterapp.activities.ChangePassword;
import com.example.barterapp.activities.ChoosePaymentMethodTwo;
import com.example.barterapp.activities.TermsAndConditions;
import com.example.barterapp.activities.NotificationsSettings;
import com.example.barterapp.others.Preferences;
import com.example.barterapp.responses.menu.LogoutResponse;
import com.example.barterapp.utils.URLs;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

public class Menu extends Fragment implements View.OnClickListener {
    private FrameLayout layoutTitle;
    LinearLayout layoutWallet, layoutPayMethod, layoutTradeHistory, layoutNotification, layoutHistory, layoutTandConditions,
            layoutChangePass, layoutLogout;
    private Preferences preferences;
    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        customProgressDialog(getContext());
        layoutTitle = getActivity().findViewById(R.id.title_layout);
        preferences = new Preferences(getContext());

        layoutWallet = view.findViewById(R.id.wallet_layout);
        layoutWallet.setOnClickListener(this);
        layoutPayMethod = view.findViewById(R.id.pay_method_layout);
        layoutPayMethod.setOnClickListener(this);
        layoutTradeHistory = view.findViewById(R.id.trade_history_layout);
        layoutTradeHistory.setOnClickListener(this);

        layoutNotification = view.findViewById(R.id.notification_layout);
        layoutNotification.setOnClickListener(this);
        layoutHistory = view.findViewById(R.id.history_layout);
        layoutHistory.setOnClickListener(this);
        layoutTandConditions = view.findViewById(R.id.terms_condition_layout);
        layoutTandConditions.setOnClickListener(this);
        layoutChangePass = view.findViewById(R.id.change_pass_layout);
        layoutChangePass.setOnClickListener(this);
        layoutLogout = view.findViewById(R.id.logout_layout);
        layoutLogout.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.wallet_layout:
                startActivity(new Intent(getContext(), Wallet.class));
                break;
            case R.id.pay_method_layout:
                startActivity(new Intent(getContext(), ChoosePaymentMethodTwo.class));
                break;
            case R.id.notification_layout:
                startActivity(new Intent(getContext(), NotificationsSettings.class));
                break;
            case R.id.trade_history_layout:
                startActivity(new Intent(getContext(), TradesHistory.class));
                break;
            case R.id.history_layout:
                startActivity(new Intent(getContext(), History.class));
                break;
            case R.id.terms_condition_layout:
                startActivity(new Intent(getContext(), TermsAndConditions.class));
                break;
            case R.id.change_pass_layout:
                startActivity(new Intent(getContext(), ChangePassword.class));
                break;
            case R.id.logout_layout:
                logOut();
                // preferences.setToken("");
                break;
        }
    }

    private void logOut() {
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        final Gson gson = new GsonBuilder().create();
        StringRequest request = new StringRequest(Request.Method.POST, URLs.logout_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    LogoutResponse logoutResponse = gson.fromJson(response, LogoutResponse.class);
                    preferences.setToken("");
                    Toast.makeText(getContext(), "" + logoutResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getContext(),SignInActivity.class));
                    ((Activity)getContext()).finish();
                    progressDialog.dismiss();
                }catch (Exception e){
                    Log.e("Logout e",e.toString());
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());
                progressDialog.dismiss();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headerMap = new HashMap<>();
                String token = preferences.getToken();
                headerMap.put("Authorization", "Bearer " + token);
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
