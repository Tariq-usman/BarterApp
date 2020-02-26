package com.example.barterapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.barterapp.R;
import com.example.barterapp.adapters.ChatAdapter;
import com.example.barterapp.fragments.dialog_fragments.DialogFragmentInvoice;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView recyclerView;
    private ImageView imageView;
    DialogFragmentInvoice fragmentInvoice;
    private ImageView generateInvoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        fragmentInvoice = new DialogFragmentInvoice();
        generateInvoice = findViewById(R.id.view_get_custom_offers);
        generateInvoice.setOnClickListener(this);
        imageView = findViewById(R.id.iv_back_chat);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recyclerView = findViewById(R.id.recycler_view_chat);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(new ChatAdapter(getApplicationContext()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.view_get_custom_offers:
                startActivity(new Intent(ChatActivity.this, OfferAcceptReject.class));
                break;
//                fragmentInvoice.show(getSupportFragmentManager(),"Invoice Dialog");
        }

    }
}
