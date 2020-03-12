package com.example.barterapp.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.barterapp.R;
import com.example.barterapp.adapters.HomeAdapter;
import com.example.barterapp.adapters.HomeSearchAdapter;
import com.example.barterapp.others.Preferences;
import com.example.barterapp.responses.home.GetAllJobsResponse;
import com.example.barterapp.responses.home.SearchJobResponse;
import com.example.barterapp.utils.URLs;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Home extends Fragment {
    private RecyclerView recyclerView, recyclerViewSearch;
    ProgressDialog progressDialog;
    List<GetAllJobsResponse.AllJob> allJobList = new ArrayList<>();
    List<SearchJobResponse.Job> searchList = new ArrayList<>();
    private ImageView ivSearch;
    private EditText etSearch;
    private TextView tvNoResult;
    HomeAdapter homeAdapter;
    HomeSearchAdapter searchAdapter;
    private Preferences preferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        customProgressDialog(getContext());
        preferences = new Preferences(getContext());
        etSearch = view.findViewById(R.id.et_search_home);
        tvNoResult = view.findViewById(R.id.tvNo_resutl_found);


        recyclerView = view.findViewById(R.id.recycler_view_home);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        homeAdapter = new HomeAdapter(getContext(), allJobList);
        recyclerView.setAdapter(homeAdapter);

        recyclerViewSearch = view.findViewById(R.id.recycler_view_search_home);
        recyclerViewSearch.setHasFixedSize(true);
        recyclerViewSearch.setLayoutManager(new LinearLayoutManager(getContext()));
        searchAdapter = new HomeSearchAdapter(getContext(), searchList);
        recyclerViewSearch.setAdapter(searchAdapter);

        ivSearch = view.findViewById(R.id.iv_search_home);
        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences.setSearchVal(true);
                String search = etSearch.getText().toString().trim();
                if (search == null || search.isEmpty()) {
                    Toast.makeText(getContext(), "Enter your desire search..", Toast.LENGTH_SHORT).show();
                } else {
                    recyclerViewSearch.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                    InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    searchJob();
                }
            }
        });
        getAllJobs();

        return view;
    }

    private void searchJob() {
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        final Gson gson = new GsonBuilder().create();
        StringRequest request = new StringRequest(Request.Method.POST, URLs.search_job_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                SearchJobResponse searchJobResponse = gson.fromJson(response, SearchJobResponse.class);
                searchList.clear();
                for (SearchJobResponse.Job searchJobs : searchJobResponse.getJobs()) {
                    searchList.add(searchJobs);
                }
                searchAdapter.notifyDataSetChanged();
                if (searchList.size() == 0) {
                    tvNoResult.setVisibility(View.VISIBLE);
                } else {
                    tvNoResult.setVisibility(View.GONE);
                }
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
                Map<String, String> stringMap = new HashMap<>();
                String title = etSearch.getText().toString().trim();
                stringMap.put("title", title);
                return stringMap;
            }
        };

        requestQueue.add(request);

    }

    private void getAllJobs() {
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        final Gson gson = new GsonBuilder().create();
        StringRequest request = new StringRequest(Request.Method.GET, URLs.get_all_jobs_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                GetAllJobsResponse jobsResponse = gson.fromJson(response, GetAllJobsResponse.class);
                allJobList.clear();
                for (GetAllJobsResponse.AllJob allJob : jobsResponse.getAllJob()) {
                    allJobList.add(allJob);
                }
                homeAdapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
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
