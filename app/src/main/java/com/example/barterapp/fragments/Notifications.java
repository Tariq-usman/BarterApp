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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.barterapp.R;
import com.example.barterapp.activities.CustomOffer;
import com.example.barterapp.adapters.NotificationsAdapter;
import com.example.barterapp.others.Preferences;
import com.example.barterapp.responses.menu.GetAllUsersNotificationsResponse;
import com.example.barterapp.utils.URLs;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Notifications extends Fragment {
    private ProgressDialog progressDialog;
    private Preferences preferences;
    private RecyclerView recyclerView;
    private NotificationsAdapter notificationsAdapter;
    private List<GetAllUsersNotificationsResponse.NotificationStreaming> notifications_list = new ArrayList<>();
    int[] images = {R.drawable.notification_image, R.drawable.arslan, R.drawable.farmer_four, R.drawable.farmer_three, R.drawable.customer};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notificatins, container, false);
        preferences = new Preferences(getContext());
        customProgressDialog(getContext());
        getAllNotifications();

        recyclerView = view.findViewById(R.id.recycler_view_notifications);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        notificationsAdapter = new NotificationsAdapter(getContext(),notifications_list);
        recyclerView.setAdapter( notificationsAdapter);
        return view;
    }

    private void getAllNotifications() {
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        Gson gson = new GsonBuilder().create();
        StringRequest request = new StringRequest(Request.Method.GET, URLs.get_all_users_notifications, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                GetAllUsersNotificationsResponse notificationsResponse = gson.fromJson(response, GetAllUsersNotificationsResponse.class);
                notifications_list.clear();
                for (int i = 0; i < notificationsResponse.getNotificationStreaming().size(); i++) {
                    notifications_list.add(notificationsResponse.getNotificationStreaming().get(i));
                }
                notificationsAdapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
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
