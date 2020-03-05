package com.example.barterapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.barterapp.R;
import com.example.barterapp.adapters.AddTradesAdapter;
import com.example.barterapp.adapters.ReturnTradesAdapter;
import com.example.barterapp.others.Preferences;
import com.example.barterapp.responses.home.CreateOfferResponse;
import com.example.barterapp.utils.URLs;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomOffer extends AppCompatActivity implements View.OnClickListener {
    private Button sendOffer;
    Context context;
    private Dialog myDialog;
    private RecyclerView recyclerViewReturnTrades, recyclerViewTrades;
    // private String[] trades_list = {"Design", "Mobile App Design"};
    private FlexboxLayoutManager layoutManager;
    private FlexboxLayoutManager fLayoutManager;
    private ImageView cancelBtn;
    private Button sendInvoice, btnYes, btnNo;
    ;
    private RadioButton rb_getPay, rb_returnService;
    private RadioGroup radioGroup;
    private LinearLayout layoutBudget, layoutSecurityAmount, layoutReturnTrades;
    private ImageButton addNewTrade;
    private List<String> trades_list;
    private List<String> returnTradesList;
    private TextView tradesView, tvDueDate;
    private EditText etPrice, etSecurityAmount, etDescription;
    private AddTradesAdapter addTradesAdapter;
    private ReturnTradesAdapter returnTradesAdapter;
    Preferences preferences;
    private int jobId;
    private String trades, due_date, budget, offerType;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_offer);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        customProgressDialog(CustomOffer.this);

        preferences = new Preferences(this);
        getIncommingIntent();

        trades_list = new ArrayList<>(Arrays.asList(trades.split(",")));
        returnTradesList = new ArrayList<>();

        tvDueDate = findViewById(R.id.tvDue_date_custom_invoice);
        tvDueDate.setText(due_date);
        etPrice = findViewById(R.id.etPrice_custom_invoice);
        etSecurityAmount = findViewById(R.id.etSecurity_amount_custom_invoice);
        etDescription = findViewById(R.id.etDescription_custom_invoice);
        radioGroup = findViewById(R.id.radio_group_custom_offer);
        rb_getPay = findViewById(R.id.rb_get_pay_custom_offer);
        rb_getPay.setOnClickListener(this);
        rb_returnService = findViewById(R.id.rb_return_service_custom_offer);
        rb_returnService.setOnClickListener(this);
        addNewTrade = findViewById(R.id.btn_add_new_trade_custom_offer);
        addNewTrade.setOnClickListener(this);

        layoutBudget = findViewById(R.id.layout_budget);
        layoutSecurityAmount = findViewById(R.id.security_amount_dialog_custom_offer);
        layoutReturnTrades = findViewById(R.id.return_trades_custom_offer);
        if (rb_getPay.isChecked()) {
            offerType = "2";
            layoutSecurityAmount.setVisibility(View.GONE);
            layoutReturnTrades.setVisibility(View.GONE);
            layoutBudget.setVisibility(View.VISIBLE);
        } else if (rb_returnService.isChecked()) {
            layoutSecurityAmount.setVisibility(View.VISIBLE);
            layoutReturnTrades.setVisibility(View.VISIBLE);
            layoutBudget.setVisibility(View.GONE);
        }
        cancelBtn = findViewById(R.id.cancel_btn_custom_offer);
        cancelBtn.setOnClickListener(this);

        //Recycler for trades
        recyclerViewTrades = findViewById(R.id.recycler_view_custom_offer);
        layoutManager = new FlexboxLayoutManager(getApplicationContext());
        layoutManager.setFlexWrap(FlexWrap.WRAP);
        layoutManager.setAlignItems(AlignItems.STRETCH);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        recyclerViewTrades.setLayoutManager(layoutManager);
        addTradesAdapter = new AddTradesAdapter(getApplicationContext(), trades_list);
        recyclerViewTrades.setAdapter(addTradesAdapter);
        addTradesAdapter.notifyDataSetChanged();

        //Recycler for return trades
        recyclerViewReturnTrades = findViewById(R.id.recycler_view_custom_offer_return_trades);
        fLayoutManager = new FlexboxLayoutManager(getApplicationContext());
        fLayoutManager.setFlexWrap(FlexWrap.WRAP);
        fLayoutManager.setAlignItems(AlignItems.STRETCH);
        fLayoutManager.setFlexDirection(FlexDirection.ROW);
        fLayoutManager.setJustifyContent(JustifyContent.FLEX_START);
        recyclerViewReturnTrades.setLayoutManager(fLayoutManager);
        returnTradesAdapter = new ReturnTradesAdapter(getApplicationContext(), returnTradesList);
        recyclerViewReturnTrades.setAdapter(returnTradesAdapter);
        sendInvoice = findViewById(R.id.send_invoice_custom_offer);
        sendInvoice.setOnClickListener(this);


    }

    private void getIncommingIntent() {
        jobId = getIntent().getIntExtra("jobId", 0);
        trades = getIntent().getStringExtra("trades");
        due_date = getIntent().getStringExtra("due_date");
        budget = getIntent().getStringExtra("budget");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_new_trade_custom_offer:
                AddTrades();
                break;
            case R.id.send_invoice_custom_offer:
                if (rb_getPay.isChecked()) {
                    if (etPrice.getText().toString() == null || etPrice.getText().toString().isEmpty()) {
                        etPrice.setError("Enter estimated budget please");
                    }else if (etDescription.getText().toString().isEmpty() || etDescription.getText().toString() == null) {
                        etDescription.setError("Enter description please..");
                    }else {
                        sendCustomOffer();
                    }
                } else if (rb_returnService.isChecked()) {
                    if (etSecurityAmount.getText().toString().isEmpty() || etSecurityAmount.getText().toString() == null) {
                        etSecurityAmount.setError("Enter security amount..");
                    } else if (returnTradesList.isEmpty()) {
                        Toast.makeText(CustomOffer.this, "Add return trades please", Toast.LENGTH_SHORT).show();
                    }else if (etDescription.getText().toString().isEmpty() || etDescription.getText().toString() == null) {
                        etDescription.setError("Enter description please..");
                    }else {
                        sendCustomOffer();
                    }
                }
                break;
            case R.id.cancel_btn_custom_offer:
                finish();
                break;
            case R.id.rb_get_pay_custom_offer:
                offerType = "2";
                layoutSecurityAmount.setVisibility(View.GONE);
                layoutReturnTrades.setVisibility(View.GONE);
                layoutBudget.setVisibility(View.VISIBLE);
                break;
            case R.id.rb_return_service_custom_offer:
                offerType = "1";
                layoutSecurityAmount.setVisibility(View.VISIBLE);
                layoutReturnTrades.setVisibility(View.VISIBLE);
                layoutBudget.setVisibility(View.GONE);
                break;

        }
    }

    public void AddTrades() {
        myDialog = new Dialog(CustomOffer.this);
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        myDialog.setContentView(R.layout.customdialog);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        tradesView = myDialog.findViewById(R.id.et_add_trades);
        btnYes = myDialog.findViewById(R.id.btn_add);
        btnNo = myDialog.findViewById(R.id.btn_no);
        btnYes.setEnabled(true);
        btnNo.setEnabled(true);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trade = tradesView.getText().toString().trim();
                if (!trade.isEmpty() && trade != null && trade != "") {
                    returnTradesList.add(trade);
                    returnTradesAdapter.notifyItemInserted(returnTradesList.size());
                    Log.i("trades", trade);
                    myDialog.cancel();
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

    private void sendCustomOffer() {
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(CustomOffer.this);

        Gson gson = new GsonBuilder().create();
        StringRequest request = new StringRequest(Request.Method.POST, URLs.create_offer_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(CustomOffer.this, "Create offer successfully..", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MainPage.class);
                intent.putExtra("fragment_status", "notification");
                startActivity(intent);
                finish();
                progressDialog.dismiss();
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

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("offer_type", offerType);
                if (rb_returnService.isChecked()) {
                    StringBuilder string_return_trades = new StringBuilder();
                    for (String str : returnTradesList) {
                        string_return_trades.append(str + ", ");
                        string_return_trades.append("\t");
                    }
                    map.put("offer", string_return_trades.toString());
                    String barterSecurity = etSecurityAmount.getText().toString().trim();
                    map.put("barter_security", barterSecurity);
                } else if (rb_getPay.isChecked()) {
                    String price = etPrice.getText().toString().trim();
                    map.put("offer", price);
                    map.put("barter_security", "0");
                }
                map.put("job_id", String.valueOf(jobId));
                String desc = etDescription.getText().toString().trim();
                map.put("description", desc);
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
