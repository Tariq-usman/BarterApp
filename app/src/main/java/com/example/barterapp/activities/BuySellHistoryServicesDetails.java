package com.example.barterapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.barterapp.R;
import com.example.barterapp.adapters.ServiceDetailsTradesAdapter;
import com.example.barterapp.fragments.dialog_fragments.DialogFragmentRating;
import com.example.barterapp.others.Preferences;
import com.example.barterapp.utils.URLs;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class BuySellHistoryServicesDetails extends AppCompatActivity {
    long daysDiff;
    private ImageView backBtn, ivPostedBy;
    private Button completeBtn;
    private Preferences preferences;
    private String status;
    private RecyclerView recyclerView, recyclerViewTrades;
    private FlexboxLayoutManager layoutManager;
    private String title, description, posted_by, picture, trades, location, duration, due_date;
    private TextView tvTitle, tvDescription, tvPosted_by, tvTrades, tvLocation, tvDuration, tvDue_date;
    private int budget, orderId;
    private List<String> trades_list;
    private ProgressDialog progressDialog;
    private int order_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_sell_history_services_details);
        customProgressDialog(BuySellHistoryServicesDetails.this);

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
        getIncomingIntent();
        ivPostedBy = findViewById(R.id.ivPosted_by_offer_details);
        tvTitle = findViewById(R.id.tvTitle_offer_details);
        tvDescription = findViewById(R.id.tvDescription_offer_details);
        tvPosted_by = findViewById(R.id.tvPosted_by_offer_details);
        tvLocation = findViewById(R.id.tvLocation_offer_details);
        tvDuration = findViewById(R.id.tvDuration_offer_details);
        tvDue_date = findViewById(R.id.tvDue_date_offer_details);
        if (getIntent() != null) {
            tvTitle.setText(title);
            tvDescription.setText(description);
            tvPosted_by.setText(posted_by);
            Glide.with(getApplicationContext()).load(URLs.image_url + picture).into(ivPostedBy);
            trades_list = new ArrayList<>(Arrays.asList(trades.split(",")));
            tvLocation.setText(location);
            tvDue_date.setText(due_date);

            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String current_date = df.format(c);
             due_date = duration;

            daybetween(current_date/*"25/02/2020"*/, due_date /*"28/02/2020"*/, "yyyy-MM-dd");
            if (daysDiff <= 0) {
                tvDuration.setText("0 Days");
            } else {
                tvDuration.setText(daysDiff+" Days");
            }
        } else {
            Toast.makeText(this, "No data available!", Toast.LENGTH_SHORT).show();
        }

        recyclerViewTrades = findViewById(R.id.recycler_view_trades_service_details);
        layoutManager = new FlexboxLayoutManager(getApplicationContext());
        layoutManager.setFlexWrap(FlexWrap.WRAP);
        layoutManager.setAlignItems(AlignItems.STRETCH);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        recyclerViewTrades.setLayoutManager(layoutManager);
        recyclerViewTrades.setAdapter(new ServiceDetailsTradesAdapter(getApplicationContext(), trades_list));

        if (status.equalsIgnoreCase("buy services")) {
            completeBtn.setVisibility(View.VISIBLE);
        } else if (status.equalsIgnoreCase("history")) {
            completeBtn.setVisibility(View.GONE);
        }

        completeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //preferences.setOrderId(orderId);
                 order_id = preferences.getOrderId();
                completeOrder();

            }
        });
    }

    private void completeOrder() {
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.POST, URLs.complete_order_url + order_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                DialogFragmentRating dialogFragmentRating = new DialogFragmentRating();
                dialogFragmentRating.show(getSupportFragmentManager(), "Rating");
//                finish();
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(BuySellHistoryServicesDetails.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                Log.e("history error", error.toString());
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
                map.put("status_id", "2");
                return map;
            }
        };
        requestQueue.add(request);
    }

    private void getIncomingIntent() {
        orderId = getIntent().getIntExtra("order_id", 0);
        title = getIntent().getStringExtra("title_my_job");
        description = getIntent().getStringExtra("description_my_job");
        posted_by = getIntent().getStringExtra("posted_by_my_job");
        picture = getIntent().getStringExtra("picture_my_job");
        trades = getIntent().getStringExtra("trades_my_job");
        location = getIntent().getStringExtra("location_my_job");
        duration = getIntent().getStringExtra("duration_my_job");
        due_date = getIntent().getStringExtra("due_date_my_job");
        budget = getIntent().getIntExtra("budget_my_job", 0);
    }

    public long daybetween(String date1, String date2, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.ENGLISH);
        Date Date1 = null, Date2 = null;
        try {
            Date1 = sdf.parse(date1);
            Date2 = sdf.parse(date2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        daysDiff = (Date2.getTime() - Date1.getTime()) / (24 * 60 * 60 * 1000);
        return (Date2.getTime() - Date1.getTime()) / (24 * 60 * 60 * 1000);
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
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
