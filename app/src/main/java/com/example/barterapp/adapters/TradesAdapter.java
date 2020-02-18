package com.example.barterapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barterapp.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class TradesAdapter extends RecyclerView.Adapter<TradesAdapter.ViewHolder> {
    Context context;
    String[] trades;
    public TradesAdapter(Context context, String[] trades) {
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
        holder.textView.setText(trades[position]);

    }

    @Override
    public int getItemCount() {
        return trades.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_trade_text);
          //  imageView = itemView.findViewById(R.id.iv_home_products);
        }
    }
}
