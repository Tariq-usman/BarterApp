package com.example.barterapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barterapp.R;
import com.example.barterapp.activities.MainPage;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

public class CustomOffersAdapter extends RecyclerView.Adapter<CustomOffersAdapter.ViewHolder> {
    private Context context;
    private String[] trades = {"Design", "Mobile App Design", "Web Design", "abc", "abc", "Design"};
    private String[] returnTrades = {"Design", "Mobile App Design"};
    private FlexboxLayoutManager layoutManager;
    private FlexboxLayoutManager fLayoutManager;

    public CustomOffersAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_items_custom_offers, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        //Recycler for trades
        layoutManager = new FlexboxLayoutManager(context);
        layoutManager.setFlexWrap(FlexWrap.WRAP);
        layoutManager.setAlignItems(AlignItems.STRETCH);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        holder.recyclerViewTrades.setLayoutManager(layoutManager);
        holder.recyclerViewTrades.setAdapter(new TradesAdapter(context, trades));

        //Recycler for return trades
        fLayoutManager = new FlexboxLayoutManager(context);
        fLayoutManager.setFlexWrap(FlexWrap.WRAP);
        fLayoutManager.setAlignItems(AlignItems.STRETCH);
        fLayoutManager.setFlexDirection(FlexDirection.ROW);
        fLayoutManager.setJustifyContent(JustifyContent.FLEX_START);
        holder.recyclerViewReturnTrades.setLayoutManager(fLayoutManager);
//        holder.recyclerViewReturnTrades.setAdapter(new ReturnTradesAdapter(context, returnTrades));


        holder.acceptOfferBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainPage.class);
                intent.putExtra("fragment_status", "tasks");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                ((Activity) context).finish();
            }
        });

    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private Button acceptOfferBtn;
        private RecyclerView recyclerViewReturnTrades, recyclerViewTrades;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            acceptOfferBtn = itemView.findViewById(R.id.accept_offer_btn);
            recyclerViewTrades = itemView.findViewById(R.id.recycler_view_chat_invoice_trades);
            recyclerViewReturnTrades = itemView.findViewById(R.id.recycler_view_chat_invoice_return_trades);
        }
    }
}
