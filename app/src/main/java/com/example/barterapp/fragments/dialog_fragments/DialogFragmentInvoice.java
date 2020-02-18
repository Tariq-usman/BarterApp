package com.example.barterapp.fragments.dialog_fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barterapp.R;
import com.example.barterapp.adapters.ReturnTradesAdapter;
import com.example.barterapp.adapters.TradesAdapter;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

public class DialogFragmentInvoice extends DialogFragment {
    Context context;
    private RecyclerView recyclerViewReturnTrades, recyclerViewTrades;
    private String[] trades = {"Design", "Mobile App Design", "Web Design", "abc", "abc", "Design"};
    private String[] returnTrades = {"Design", "Mobile App Design"};
    private FlexboxLayoutManager layoutManager;
    private FlexboxLayoutManager fLayoutManager;
    private ImageView cancelBtn;
    private Button sendInvoice;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_framgment_invoice, container, false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
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
        recyclerViewTrades.setAdapter(new TradesAdapter(getContext(), trades));

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
}
