package com.example.barterapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
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
import com.example.barterapp.adapters.AcceptRejectOfferAdapter;
import com.example.barterapp.others.Preferences;
import com.example.barterapp.responses.chat_responses.GetAllUserOfferResponse;
import com.example.barterapp.utils.URLs;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OfferAcceptReject extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ImageView ivBack;
    private Preferences preferences;
    private ProgressDialog progressDialog;
    AcceptRejectOfferAdapter acceptRejectOfferAdapter;
    private List<GetAllUserOfferResponse.Offer> offerList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_accept_reject);
        customProgressDialog(OfferAcceptReject.this);
        ivBack = findViewById(R.id.iv_back_offer_accept_reject);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        preferences = new Preferences(this);
        getOfferData();
        recyclerView = findViewById(R.id.recycler_view_custom_offer_accept_reject);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        acceptRejectOfferAdapter = new AcceptRejectOfferAdapter(OfferAcceptReject.this,offerList);
        recyclerView.setAdapter(acceptRejectOfferAdapter);
    }

    private void getOfferData() {
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        Gson gson = new GsonBuilder().create();
        StringRequest request = new StringRequest(Request.Method.GET, URLs.get_offers_url + preferences.getJobUserId(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                GetAllUserOfferResponse userOfferResponse = gson.fromJson(response,GetAllUserOfferResponse.class);
                for (int i = 0 ; i<userOfferResponse.getOffers().size();i++){
                    offerList.add(userOfferResponse.getOffers().get(i));
                }
                acceptRejectOfferAdapter.notifyDataSetChanged();
                Toast.makeText(OfferAcceptReject.this, "response", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(OfferAcceptReject.this, "error", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }){
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
