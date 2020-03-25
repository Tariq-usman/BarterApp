package com.example.barterapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.bumptech.glide.Glide;
import com.example.barterapp.R;
import com.example.barterapp.adapters.DeleteProfilePortfolioAdapter;
import com.example.barterapp.adapters.ProfilePortfolioAdapter;
import com.example.barterapp.adapters.ProfileTradesAdapter;
import com.example.barterapp.others.Preferences;
import com.example.barterapp.responses.profile.CurrentUserResponse;
import com.example.barterapp.utils.URLs;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Portfolio extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ImageView imageViewBack;
    private Preferences preferences;
    private ProgressDialog progressDialog;
    private DeleteProfilePortfolioAdapter portfolioAdapter;
    public static List<CurrentUserResponse.User.Portfolio> portfolio_pics = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio);
        preferences = new Preferences(getApplicationContext());
        customProgressDialog(Portfolio.this);

        imageViewBack = findViewById(R.id.iv_back_portfolio);
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        currentUser();

        recyclerView = findViewById(R.id.recycler_view_portfolio);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
        portfolioAdapter = new DeleteProfilePortfolioAdapter(getApplicationContext(),portfolio_pics);
        recyclerView.setAdapter(portfolioAdapter);
    }
    private void currentUser() {
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        final Gson gson = new GsonBuilder().create();
        StringRequest request = new StringRequest(Request.Method.GET, URLs.current_user_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("Response", response);
                    CurrentUserResponse userResponse = gson.fromJson(response, CurrentUserResponse.class);

                    String userName = userResponse.getUser().getName();
                    String experience = userResponse.getUser().getExperience();
                    String completed_tasks = userResponse.getUser().getCompleteOrder().toString();
                    String trades = userResponse.getUser().getTrades();
                    String profileImage = URLs.image_url + userResponse.getUser().getPicture();

                    portfolio_pics.clear();
                    for (int i = 0; i < userResponse.getUser().getPortfolios().size(); i++) {
                        portfolio_pics.add(userResponse.getUser().getPortfolios().get(i));
                    }
                    portfolioAdapter.notifyDataSetChanged();
                    progressDialog.dismiss();

                } catch (Exception e) {
                    Log.e("Exception", e.toString());
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());
                Toast.makeText(Portfolio.this, "Error", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
