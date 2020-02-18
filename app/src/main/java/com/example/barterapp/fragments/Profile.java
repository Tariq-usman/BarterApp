package com.example.barterapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barterapp.activities.Portfolio;
import com.example.barterapp.R;
import com.example.barterapp.activities.EditPortfolio;
import com.example.barterapp.adapters.ProfilePortfolioAdapter;
import com.example.barterapp.adapters.TradesAdapter;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

public class Profile extends Fragment implements View.OnClickListener {
    private LinearLayout layoutEdit;
    private TextView textViewPortfolio;
    private RecyclerView recyclerView, recyclerViewTrades;
    private FlexboxLayoutManager layoutManager;
    private EditText experienceView;
    private String[] trades = {"Design", "Mobile App Design", "Web Design", "abc", "abc", "Design"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        experienceView = view.findViewById(R.id.experience_view);
        layoutEdit = view.findViewById(R.id.layout_edit);
        layoutEdit.setOnClickListener(this);
        textViewPortfolio = view.findViewById(R.id.tv_portfolio_profile);
        textViewPortfolio.setOnClickListener(this);

        recyclerViewTrades = view.findViewById(R.id.recycler_view_profile_trades);
        layoutManager = new FlexboxLayoutManager(getContext());
        layoutManager.setFlexWrap(FlexWrap.WRAP);
        layoutManager.setAlignItems(AlignItems.STRETCH);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        recyclerViewTrades.setLayoutManager(layoutManager);
        recyclerViewTrades.setAdapter(new TradesAdapter(getContext(), trades));

        recyclerView = view.findViewById(R.id.recycler_view_profile_portfolio);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setAdapter(new ProfilePortfolioAdapter(getContext()));
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layout_edit:
                experienceView.setFocusable(true);
                experienceView.setClickable(true);
                experienceView.setCursorVisible(true);
                experienceView.setFocusableInTouchMode(true);
                break;
            case R.id.tv_portfolio_profile:
                startActivity(new Intent(getContext(), Portfolio.class));

                break;
        }
    }
}
