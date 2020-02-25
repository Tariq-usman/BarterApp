package com.example.barterapp.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.barterapp.R;
import com.example.barterapp.adapters.HomeAdapter;
import com.example.barterapp.responses.GetAllJobsResponse;
import com.example.barterapp.utils.URLs;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class Home extends Fragment {
    int[] images ={R.drawable.notification_image,R.drawable.arslan,R.drawable.farmer_four,R.drawable.farmer_three,R.drawable.customer};
    private RecyclerView recyclerView;
    ProgressDialog progressDialog;
    List<GetAllJobsResponse.AllJob> allJobList=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        customProgressDialog(getContext());

        recyclerView = view.findViewById(R.id.recycler_view_home);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        
        
        getAllJobs();
        
        return view;
    }

    private void getAllJobs() {
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        final Gson gson = new GsonBuilder().create();
        StringRequest request = new StringRequest(Request.Method.GET, URLs.get_all_jobs_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                GetAllJobsResponse jobsResponse = gson.fromJson(response,GetAllJobsResponse.class);
                for (GetAllJobsResponse.AllJob allJob : jobsResponse.getAllJob()){
                    allJobList.add(allJob);
                }
                recyclerView.setAdapter(new HomeAdapter(getContext(),allJobList));
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(request);
    }

    public void customProgressDialog(Context context){
        progressDialog = new ProgressDialog(context);
        // Setting Message
        progressDialog.setMessage("Loading...");
        // Progress Dialog Style Spinner
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // Fetching max value
        progressDialog.getMax();
    }
}
