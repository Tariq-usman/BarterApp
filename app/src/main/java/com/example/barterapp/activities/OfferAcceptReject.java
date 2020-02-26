package com.example.barterapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.barterapp.R;
import com.example.barterapp.adapters.CustomOffersAdapter;

public class OfferAcceptReject extends AppCompatActivity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_accept_reject);
        recyclerView = findViewById(R.id.recycler_view_custom_offer_accept_reject);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(new CustomOffersAdapter(OfferAcceptReject.this));
    }
}
