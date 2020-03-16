package com.example.barterapp.fragments.menu.history;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.barterapp.R;
import com.example.barterapp.adapters.menu.history.UserOffersHistoryAdapter;
import com.example.barterapp.others.Preferences;
import com.example.barterapp.responses.menu.AllUserJobsHistoryResponse;
import com.example.barterapp.utils.URLs;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserOffersFragment extends Fragment {
    private ProgressDialog progressDialog;
    private RecyclerView recyclerView;
    private ImageView backBtn;
    UserOffersHistoryAdapter userOffersHistoryAdapter;
    private Preferences preferences;
    List<AllUserJobsHistoryResponse.SellJob> sellJobs = new ArrayList<>();
    List<AllUserJobsHistoryResponse.BuyJob> buyJobs = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_offers, container, false);
        customProgressDialog(getContext());
        preferences = new Preferences(getContext());
        getAllJobsHistory();
        recyclerView = view.findViewById(R.id.recycler_view_user_offers_history);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        userOffersHistoryAdapter = new UserOffersHistoryAdapter(getContext(), buyJobs);
        recyclerView.setAdapter(userOffersHistoryAdapter);
        return view;
    }

    private void getAllJobsHistory() {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        final Gson gson = new GsonBuilder().create();
        StringRequest request = new StringRequest(Request.Method.GET, URLs.user_job_history_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("RESPONSE ", response);
                    AllUserJobsHistoryResponse historyResponse = gson.fromJson(response, AllUserJobsHistoryResponse.class);
                    for (AllUserJobsHistoryResponse.BuyJob responseOffers : historyResponse.getBuyJobs()) {
                        buyJobs.add(responseOffers);
                    }
                    userOffersHistoryAdapter.notifyDataSetChanged();
                    progressDialog.dismiss();

//                Toast.makeText(History.this, "Response", Toast.LENGTH_SHORT).show();
                } catch (IllegalStateException | JsonSyntaxException exception) {
                    Log.e("Exception", exception.toString());
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
                headerMap.put("Accept", "application/json");
                headerMap.put("Content-Type", "application/json");
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
