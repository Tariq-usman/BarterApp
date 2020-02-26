package com.example.barterapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.barterapp.R;
import com.example.barterapp.adapters.AddTradesAdapter;
import com.example.barterapp.adapters.ReturnTradesAdapter;
import com.example.barterapp.others.Preferences;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.ArrayList;
import java.util.List;

public class CustomOffer extends AppCompatActivity implements View.OnClickListener {
    private Button sendOffer;
    Context context;
    private Dialog myDialog;
    private RecyclerView recyclerViewReturnTrades, recyclerViewTrades;
    private String[] trades = {"Design", "Mobile App Design", "Web Design", "abc", "abc", "Design"};
    private String[] returnTrades = {"Design", "Mobile App Design"};
    private FlexboxLayoutManager layoutManager;
    private FlexboxLayoutManager fLayoutManager;
    private ImageView cancelBtn;
    private Button sendInvoice, btnYes, btnNo;
    ;
    private RadioButton rb_getPay, rb_returnService;
    private RadioGroup radioGroup;
    private LinearLayout layoutSecurityAmount, layoutReturnTrades;
    private ImageButton addNewTrade;
    private List<String> trades_list;
    private TextView tradesView;
    private AddTradesAdapter addTradesAdapter;
    Preferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_offer);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        preferences = new Preferences(this);
        trades_list = new ArrayList<>();
        addTradesAdapter = new AddTradesAdapter(getApplicationContext(), trades_list);


        radioGroup = findViewById(R.id.radio_group_custom_offer);
        rb_getPay = findViewById(R.id.rb_get_pay_custom_offer);
        rb_returnService = findViewById(R.id.rb_return_service_custom_offer);
        addNewTrade = findViewById(R.id.btn_add_new_trade_custom_offer);
        addNewTrade.setOnClickListener(this);

        layoutSecurityAmount = findViewById(R.id.security_amount_dialog_custom_offer);
        layoutReturnTrades = findViewById(R.id.return_trades_custom_offer);
        if (rb_getPay.isChecked()) {
            layoutSecurityAmount.setVisibility(View.GONE);
            layoutReturnTrades.setVisibility(View.GONE);
        } else if (rb_returnService.isChecked()) {
            layoutSecurityAmount.setVisibility(View.VISIBLE);
            layoutReturnTrades.setVisibility(View.VISIBLE);
        }

        rb_getPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                layoutSecurityAmount.setVisibility(View.GONE);
                layoutReturnTrades.setVisibility(View.GONE);
            }
        });
        rb_returnService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutSecurityAmount.setVisibility(View.VISIBLE);
                layoutReturnTrades.setVisibility(View.VISIBLE);

            }
        });


        cancelBtn = findViewById(R.id.cancel_btn_custom_offer);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //dismiss();//
            }
        });


        sendInvoice = findViewById(R.id.send_invoice_custom_offer);
        sendInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainPage.class);
                intent.putExtra("fragment_status","notification");
                startActivity(intent);
                finish();
              //  dismiss();
            }
        });


        recyclerViewTrades = findViewById(R.id.recycler_view_custom_offer);
        recyclerViewReturnTrades = findViewById(R.id.recycler_view_custom_offer_return_trades);
        //Recycler for trades
        layoutManager = new FlexboxLayoutManager(getApplicationContext());
        layoutManager.setFlexWrap(FlexWrap.WRAP);
        layoutManager.setAlignItems(AlignItems.STRETCH);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        recyclerViewTrades.setLayoutManager(layoutManager);
        recyclerViewTrades.setAdapter(addTradesAdapter);
        addTradesAdapter.notifyDataSetChanged();

        //Recycler for return trades
        fLayoutManager = new FlexboxLayoutManager(getApplicationContext());
        fLayoutManager.setFlexWrap(FlexWrap.WRAP);
        fLayoutManager.setAlignItems(AlignItems.STRETCH);
        fLayoutManager.setFlexDirection(FlexDirection.ROW);
        fLayoutManager.setJustifyContent(JustifyContent.FLEX_START);
        recyclerViewReturnTrades.setLayoutManager(fLayoutManager);
        recyclerViewReturnTrades.setAdapter(new ReturnTradesAdapter(getApplicationContext(), returnTrades));
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_new_trade_custom_offer:
//                Toast.makeText(getContext(), "Clicked", Toast.LENGTH_SHORT).show();
                AddTrades();
                break;
        }
    }
    public void AddTrades() {
        myDialog = new Dialog(CustomOffer.this);
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        myDialog.setContentView(R.layout.customdialog);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        tradesView = myDialog.findViewById(R.id.et_add_trades);
        btnYes = myDialog.findViewById(R.id.btn_add);
        btnNo = myDialog.findViewById(R.id.btn_no);
        btnYes.setEnabled(true);
        btnNo.setEnabled(true);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trade = tradesView.getText().toString().trim();
                if (!trade.isEmpty() && trade != null && trade != "") {
                    trades_list.add(trade);
                    Log.i("trades", trade);
                    myDialog.cancel();
                }
            }
        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.cancel();
            }
        });
        myDialog.show();
    }
}
