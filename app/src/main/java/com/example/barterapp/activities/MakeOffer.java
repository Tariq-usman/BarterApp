package com.example.barterapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.barterapp.R;
import com.example.barterapp.adapters.MakeOfferTradesAdapter;
import com.example.barterapp.utils.URLs;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MakeOffer extends AppCompatActivity {
    private Button tradeBtn;
    private ImageView backBtn, ivPostedBy;
    private TextView tvTitle, tvDescription, tvPosted_by, tvLocation, tvDeuDate, tvDuration, tvBudget;
    private RecyclerView recyclerView, recyclerViewTrades;
    private FlexboxLayoutManager layoutManager;
    private int job_id;
    private String title, description, posted_by, picture, trades, location, duration, due_date;
    private int budget;
    private List<String> trades_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_offer);
//        trades_list.add("xyz");
        backBtn = findViewById(R.id.iv_back_services);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getIncommingIntent();

        tvTitle = findViewById(R.id.tvTitle_make_offer);
        tvDescription = findViewById(R.id.tvDescription_make_offer);
        ivPostedBy = findViewById(R.id.ivPosted_by_make_offer);
        tvPosted_by = findViewById(R.id.tvPosted_by_make_offer);
        tvLocation = findViewById(R.id.tvLocation_make_offer);
        tvDeuDate = findViewById(R.id.tvDue_date_make_offer);
        tvDuration = findViewById(R.id.tvDuration_make_offer);
        tvBudget = findViewById(R.id.tvEstimated_budget_make_offer);
//        tvDescription = findViewById(R.id.tvDescription_make_offer);

        if (getIntent() != null) {
            tvTitle.setText(title);
            tvDescription.setText(description);
            tvPosted_by.setText(posted_by);
            Glide.with(getApplicationContext()).load(URLs.image_url + picture).into(ivPostedBy);
            if (trades !=null) {
                trades_list = new ArrayList<>(Arrays.asList(trades.split(",")));
            } else {
                Toast.makeText(this, "Trades list is null!", Toast.LENGTH_SHORT).show();
            }
//            trades_list = new ArrayList<>(Arrays.asList(trades.replaceAll("\\s", "").split(",")));
            tvLocation.setText(location);
            tvDeuDate.setText(due_date);
            tvDuration.setText(duration);
            tvBudget.setText(String.valueOf("$ " + budget));
        } else {
            Toast.makeText(this, "No data available!", Toast.LENGTH_SHORT).show();
        }

        recyclerViewTrades = findViewById(R.id.recycler_view_trades_make_offer);
        layoutManager = new FlexboxLayoutManager(getApplicationContext());
        layoutManager.setFlexWrap(FlexWrap.WRAP);
        layoutManager.setAlignItems(AlignItems.STRETCH);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        recyclerViewTrades.setLayoutManager(layoutManager);
        recyclerViewTrades.setAdapter(new MakeOfferTradesAdapter(getApplicationContext(), trades_list));

        tradeBtn = findViewById(R.id.make_offer_btn);
        tradeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MakeOffer.this, CustomOffer.class);
                intent.putExtra("jobId", job_id);
                intent.putExtra("trades", trades);
                intent.putExtra("due_date", due_date);
                intent.putExtra("budget", budget);
                startActivity(intent);
            }
        });
    }

    private void getIncommingIntent() {
        job_id = getIntent().getIntExtra("job_id", 0);
        title = getIntent().getStringExtra("title");
        description = getIntent().getStringExtra("description");
        posted_by = getIntent().getStringExtra("posted_by");
        picture = getIntent().getStringExtra("picture");
        trades = getIntent().getStringExtra("trades");
        location = getIntent().getStringExtra("location");
        duration = getIntent().getStringExtra("duration");
        due_date = getIntent().getStringExtra("due_date");
        budget = getIntent().getIntExtra("budget", 0);
    }
}
