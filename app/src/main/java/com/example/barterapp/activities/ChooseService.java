package com.example.barterapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.barterapp.others.Preferences;
import com.example.barterapp.R;
import com.example.barterapp.adapters.PaymentMethodsAdapter;

public class ChooseService extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Button confirmBtnPMOne;
    private ImageView backBtn;
    int[] images ={R.drawable.maintainance,R.drawable.design,R.drawable.electronic,R.drawable.equipments,R.drawable.furniture};
    private String[] trades = {"Design", "Mobile App Design", "abc", "Web Design", "Design"};
    Preferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_service);
        preferences = new Preferences(getApplicationContext());
        recyclerView = findViewById(R.id.recycler_view_payment_method);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(new PaymentMethodsAdapter(getApplicationContext(),images,trades));

        backBtn = findViewById(R.id.iv_back_choose_service);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        confirmBtnPMOne = findViewById(R.id.confirm_pm_one);
        confirmBtnPMOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                preferences.setFragmentStatus("notification");
                Intent intent = new Intent(getApplicationContext(), MainPage.class);
                intent.putExtra("fragment_status","notification");
                startActivity(intent);
            }
        });
    }
}
