package com.example.barterapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.barterapp.R;
import com.example.barterapp.adapters.menu.trades_history.TradesHistoryAdapter;
import com.example.barterapp.others.Preferences;
import com.example.barterapp.responses.menu.GetAllUsersNotificationsResponse;
import com.example.barterapp.responses.menu.TradesHistoryResponse;
import com.example.barterapp.utils.URLs;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TradesHistory extends AppCompatActivity {
    private ProgressDialog progressDialog;
    private Preferences preferences;
    private ImageView ivBack;
    private RecyclerView recyclerView;
    private TradesHistoryAdapter tradesHistoryAdapter;
    private List<TradesHistoryResponse.OfferHistory> historyList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trades_history);
        preferences = new Preferences(this);
        customProgressDialog(this);
        getTradesHistory();
        ivBack = findViewById(R.id.ivBack_trade);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recyclerView = findViewById(R.id.recycler_view_trades_history);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        tradesHistoryAdapter = new TradesHistoryAdapter(getApplicationContext(), historyList);
        recyclerView.setAdapter(tradesHistoryAdapter);

    }

    private void getTradesHistory() {
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        Gson gson = new GsonBuilder().create();
        StringRequest request = new StringRequest(Request.Method.GET, URLs.get_trades_history_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    TradesHistoryResponse tradesHistoryResponse = gson.fromJson(response, TradesHistoryResponse.class);
                    historyList.clear();
                    for (int i = 0; i < tradesHistoryResponse.getOfferHistory().size(); i++) {
                        historyList.add(tradesHistoryResponse.getOfferHistory().get(i));
                    }
                    tradesHistoryAdapter.notifyDataSetChanged();
                    progressDialog.dismiss();
                } catch (Exception e) {
                    Log.e("Trades history", e.toString());
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(TradesHistory.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
