package com.example.barterapp.fragments.dialog_fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.barterapp.R;
import com.example.barterapp.others.Preferences;
import com.example.barterapp.responses.menu.AmountAddToWalletResponse;
import com.example.barterapp.utils.URLs;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.stripe.android.ApiResultCallback;
import com.stripe.android.Stripe;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.android.view.CardMultilineWidget;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class DialogFragmentWallet extends DialogFragment {
    private Dialog myDialog;
    private Button btnYes, btnNo;
    private TextView amountView;
    private Preferences preferences;
    private ImageView ivColseBtn;
    private Button submitBtn;
    CardMultilineWidget cardInputWidget;
    String stripe_token, amount;
    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_framgment_wallet, container, false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        preferences = new Preferences(getContext());
        customProgressDialog(getContext());
        cardInputWidget = view.findViewById(R.id.cardInputWidget);

        ivColseBtn = view.findViewById(R.id.cancel_btn_wallet);
        ivColseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        submitBtn = view.findViewById(R.id.submit);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCardToStripe();

            }
        });
        return view;
    }

    private void addCardToStripe() {
        progressDialog.show();
        Card card = cardInputWidget.getCard();
        Stripe stripe = new Stripe(getContext(), getString(R.string.stripe_api_key));
        stripe.createCardToken(card, new ApiResultCallback<Token>() {
            @Override
            public void onSuccess(Token token) {
                Log.e("stripe token", token.getId());
                stripe_token = token.getId();
                progressDialog.dismiss();
                addAmount();
                //    dismiss();
            }

            @Override
            public void onError(@NotNull Exception e) {
                Log.e("token error", e.toString());
                progressDialog.dismiss();
            }
        });
    }

    public void addAmount() {
        myDialog = new Dialog(getContext());
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        myDialog.setContentView(R.layout.custom_dialog_for_amount);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        amountView = myDialog.findViewById(R.id.et_add_amount);
        btnYes = myDialog.findViewById(R.id.btn_add_amount);
        btnNo = myDialog.findViewById(R.id.btn_no_amount);
        btnYes.setEnabled(true);
        btnNo.setEnabled(true);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount = amountView.getText().toString().trim();
                if (!amount.isEmpty() && amount != null && amount != "") {
                    chargeAmountFromWallet();
                    Log.i("amount", amount);
                    myDialog.cancel();
                } else {
                    Toast.makeText(getContext(), "Enter amount please.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.cancel();
            }
        });
        myDialog.show();
    }

    private void chargeAmountFromWallet() {
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        Gson gson = new GsonBuilder().create();
        StringRequest request = new StringRequest(Request.Method.POST, URLs.wallet_payment_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                AmountAddToWalletResponse toWalletResponse = gson.fromJson(response, AmountAddToWalletResponse.class);
                Toast.makeText(getContext(), "" +toWalletResponse.getCharges() , Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
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

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("stripeToken", stripe_token);
                map.put("amount", amount);
                return map;
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
