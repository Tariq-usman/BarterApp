package com.example.barterapp.fragments.dialog_fragments;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barterapp.R;
import com.example.barterapp.adapters.AddTradesAdapter;
import com.example.barterapp.adapters.ReturnTradesAdapter;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.ArrayList;
import java.util.List;

public class DialogFragmentInvoice extends DialogFragment implements View.OnClickListener {
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_framgment_invoice, container, false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        trades_list = new ArrayList<>();
        addTradesAdapter = new AddTradesAdapter(getContext(), trades_list);

        radioGroup = view.findViewById(R.id.radio_group);
        rb_getPay = view.findViewById(R.id.rb_get_pay);
        rb_returnService = view.findViewById(R.id.rb_return_service);
        addNewTrade = view.findViewById(R.id.btn_add_new_trade);
        addNewTrade.setOnClickListener(this);

        layoutSecurityAmount = view.findViewById(R.id.security_amount_dialog);
        layoutReturnTrades = view.findViewById(R.id.return_trades_dialog);
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


        cancelBtn = view.findViewById(R.id.cancel_btn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        sendInvoice = view.findViewById(R.id.send_invoice);
        sendInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        recyclerViewTrades = view.findViewById(R.id.recycler_view_invoice_trades);
        recyclerViewReturnTrades = view.findViewById(R.id.recycler_view_invoice_return_trades);
        //Recycler for trades
        layoutManager = new FlexboxLayoutManager(getContext());
        layoutManager.setFlexWrap(FlexWrap.WRAP);
        layoutManager.setAlignItems(AlignItems.STRETCH);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        recyclerViewTrades.setLayoutManager(layoutManager);
        recyclerViewTrades.setAdapter(addTradesAdapter);
        addTradesAdapter.notifyDataSetChanged();

        //Recycler for return trades
        fLayoutManager = new FlexboxLayoutManager(getContext());
        fLayoutManager.setFlexWrap(FlexWrap.WRAP);
        fLayoutManager.setAlignItems(AlignItems.STRETCH);
        fLayoutManager.setFlexDirection(FlexDirection.ROW);
        fLayoutManager.setJustifyContent(JustifyContent.FLEX_START);
        recyclerViewReturnTrades.setLayoutManager(fLayoutManager);
        recyclerViewReturnTrades.setAdapter(new ReturnTradesAdapter(getContext(), returnTrades));
        return view;
    }

    public void AddTrades() {
        myDialog = new Dialog(getContext());
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_new_trade:
//                Toast.makeText(getContext(), "Clicked", Toast.LENGTH_SHORT).show();
                AddTrades();
                break;
        }
    }
}
