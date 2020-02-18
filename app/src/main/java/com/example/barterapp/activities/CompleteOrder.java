package com.example.barterapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.barterapp.R;
import com.example.barterapp.adapters.TradesAdapter;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

public class CompleteOrder extends AppCompatActivity {
    private Button orderCompleteBtn;
    private ImageView backBtn;
    private FlexboxLayoutManager layoutManager,flexboxLayoutManager;
    RecyclerView recyclerViewTrades,recyclerViewReturnTrades;
    private String[] trades = {"Design","Mobile App Design","Web Design","abc","jdhfjshdfjfjashdfkjhadflsjfljasdfja","abc","Design","Mobile App Design","Web Design"};
    private LayoutInflater inflater;
    private View layout;
    private Toast toast;
    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_order);
        backBtn = findViewById(R.id.iv_back_complete_order);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        generateCustomToast();

        recyclerViewTrades = findViewById(R.id.recycler_view_tades_complete_order);
        layoutManager = new FlexboxLayoutManager(this);
        layoutManager.setFlexWrap(FlexWrap.WRAP);
        layoutManager.setAlignItems(AlignItems.STRETCH);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        recyclerViewTrades.setLayoutManager(layoutManager);
        recyclerViewTrades.setAdapter(new TradesAdapter(getApplicationContext(),trades));

        recyclerViewReturnTrades = findViewById(R.id.recycler_view_return_tades_complete_order);
        flexboxLayoutManager= new FlexboxLayoutManager(this);
        flexboxLayoutManager.setFlexWrap(FlexWrap.WRAP);
        flexboxLayoutManager.setAlignItems(AlignItems.STRETCH);
        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);
        flexboxLayoutManager.setJustifyContent(JustifyContent.FLEX_START);
        recyclerViewReturnTrades.setLayoutManager(flexboxLayoutManager);
        recyclerViewReturnTrades.setAdapter(new TradesAdapter(getApplicationContext(),trades));

        orderCompleteBtn = findViewById(R.id.order_complete_btn);
        orderCompleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText("Order Completed");

//                Toast.makeText(CompleteOrder.this, "Complete..", Toast.LENGTH_SHORT).show();
                toast.show();
            }
        });
    }

    private void generateCustomToast() {
        inflater = getLayoutInflater();
        layout = inflater.inflate(R.layout.custom_toast,null);
        tv = layout.findViewById(R.id.txtvw);
        toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
//        toast.show();
    }
}
