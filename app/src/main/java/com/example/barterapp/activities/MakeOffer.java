package com.example.barterapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.barterapp.R;
import com.example.barterapp.adapters.TradesAdapter;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

public class MakeOffer extends AppCompatActivity {
    private Button tradeBtn;
    private ImageView backBtn;
    private RecyclerView recyclerView, recyclerViewTrades;
    private FlexboxLayoutManager layoutManager;
    private String[] trades = {"Design", "Mobile App Design", "abc", "Web Design", "Design"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_offer);
        backBtn = findViewById(R.id.iv_back_services);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerViewTrades = findViewById(R.id.recycler_view_services_trades);
        layoutManager = new FlexboxLayoutManager(getApplicationContext());
        layoutManager.setFlexWrap(FlexWrap.WRAP);
        layoutManager.setAlignItems(AlignItems.STRETCH);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        recyclerViewTrades.setLayoutManager(layoutManager);
        recyclerViewTrades.setAdapter(new TradesAdapter(getApplicationContext(), trades));

        tradeBtn = findViewById(R.id.trade_btn);
        tradeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MakeOffer.this, CustomOffer.class));
            }
        });
    }
}
