package com.example.barterapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barterapp.R;

import java.util.List;

public class MakeOfferTradesAdapter extends RecyclerView.Adapter<MakeOfferTradesAdapter.ViewHolder> {
    Context context;
    List<String> trades;
    public MakeOfferTradesAdapter(Context context, List<String> trades) {
        this.context = context;
        this.trades = trades;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_items_trades,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(trades.get(position));

    }

    @Override
    public int getItemCount() {
        return trades.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_trade_text);
          //  ivPortfolioImage = itemView.findViewById(R.id.iv_home_products);
        }
    }
}
