package com.example.barterapp.fragments;

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
import com.example.barterapp.adapters.ChatInboxAdapter;
import com.example.barterapp.others.Preferences;
import com.example.barterapp.responses.GetAllInboxMessagesResponse;
import com.example.barterapp.utils.URLs;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatInbox extends Fragment {
    RecyclerView recyclerView;
    Preferences preferences;
    ChatInboxAdapter inboxAdapter;

    List<GetAllInboxMessagesResponse.GetMessage> allInboxMessagesList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_inbox, container, false);
        preferences = new Preferences(getContext());
        getAllInboxMessages();

        recyclerView = view.findViewById(R.id.recycler_view_chat);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        inboxAdapter = new ChatInboxAdapter(getContext(),allInboxMessagesList);
        recyclerView.setAdapter(inboxAdapter);
        return view;
    }

    private void getAllInboxMessages() {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        final Gson gson = new GsonBuilder().create();
        StringRequest request = new StringRequest(Request.Method.GET, URLs.get_all_inbox_messages, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getContext(), "Response", Toast.LENGTH_SHORT).show();

                GetAllInboxMessagesResponse inboxMessagesResponse = gson.fromJson(response,GetAllInboxMessagesResponse.class);
                for(int i=0;i<inboxMessagesResponse.getGetMessages().size();i++){

                    allInboxMessagesList.add(inboxMessagesResponse.getGetMessages().get(i));
                }
                inboxAdapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();

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
}
