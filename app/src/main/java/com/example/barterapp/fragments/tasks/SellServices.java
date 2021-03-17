package com.example.barterapp.fragments.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import com.example.barterapp.adapters.tasks.SellServicesAdapter;
import com.example.barterapp.others.Preferences;
import com.example.barterapp.responses.tasks.BuySellServicesResponse;
import com.example.barterapp.utils.URLs;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellServices extends Fragment {
    private RecyclerView recyclerView;
    private SellServicesAdapter sellServicesAdapter;
    List<BuySellServicesResponse.SellJob> sellJobsList = new ArrayList<>();
    private Preferences preferences;
    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_give_services, container, false);
        customProgressDialog(getContext());
        preferences = new Preferences(getContext());

        recyclerView = view.findViewById(R.id.recycler_view_tasks);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        sellServicesAdapter = new SellServicesAdapter(getContext(), sellJobsList);
        recyclerView.setAdapter(sellServicesAdapter);
        getSellServicesData();

        return view;
    }

    private void getSellServicesData() {
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        final Gson gson = new GsonBuilder().create();
        StringRequest request = new StringRequest(Request.Method.GET, URLs.buy_sell_services_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    BuySellServicesResponse sellServicesResponse = gson.fromJson(response, BuySellServicesResponse.class);
                    sellJobsList.clear();
                    for (int i = 0; i < sellServicesResponse.getSellJob().size(); i++) {
                        sellJobsList.add(sellServicesResponse.getSellJob().get(i));
                    }
                    sellServicesAdapter.notifyDataSetChanged();
                   /* if (sellJobsList.isEmpty()) {
                        Toast.makeText(getContext(), "No data found!", Toast.LENGTH_SHORT).show();
                    }*/
                    progressDialog.dismiss();
                } catch (Exception e) {
                    Log.e("Task Exception", e.toString());
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());
                Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
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
