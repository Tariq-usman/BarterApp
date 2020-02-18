package com.example.barterapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.barterapp.activities.History;
import com.example.barterapp.R;
import com.example.barterapp.activities.Valet;
import com.example.barterapp.activities.ChangePassword;
import com.example.barterapp.activities.ChoosePaymentMethodTwo;
import com.example.barterapp.activities.TermsAndConditions;
import com.example.barterapp.activities.NotificationsSettings;

public class Menu extends Fragment implements View.OnClickListener {
    private FrameLayout layoutTitle;
    LinearLayout layoutValet,layoutPayMethod,layoutTradeHistory,layoutNotification,layoutHistory,layoutTandConditions,layoutChangePass,layoutLogout;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu,container,false);
        layoutTitle = getActivity().findViewById(R.id.title_layout);

        layoutValet = view.findViewById(R.id.valet_layout);
        layoutValet.setOnClickListener(this);
        layoutPayMethod = view.findViewById(R.id.pay_method_layout);
        layoutPayMethod.setOnClickListener(this);
        layoutTradeHistory = view.findViewById(R.id.trade_history_layout);
        layoutTradeHistory.setOnClickListener(this);

        layoutNotification = view.findViewById(R.id.notification_layout);
        layoutNotification.setOnClickListener(this);
        layoutHistory = view.findViewById(R.id.history_layout);
        layoutHistory.setOnClickListener(this);
        layoutTandConditions= view.findViewById(R.id.terms_condition_layout);
        layoutTandConditions.setOnClickListener(this);
        layoutChangePass= view.findViewById(R.id.change_pass_layout);
        layoutChangePass.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.valet_layout:
                startActivity(new Intent(getContext(), Valet.class));
                break;
            case R.id.pay_method_layout:
                startActivity(new Intent(getContext(), ChoosePaymentMethodTwo.class));
                break;
            case R.id.notification_layout:
                startActivity(new Intent(getContext(), NotificationsSettings.class));
                break;
            case R.id.history_layout:
                startActivity(new Intent(getContext(), History.class));
                break;
            case R.id.terms_condition_layout:
                startActivity(new Intent(getContext(), TermsAndConditions.class));
                break;
            case R.id.change_pass_layout:
                startActivity(new Intent(getContext(), ChangePassword.class));
                break;

        }
    }
}
