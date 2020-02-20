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

public class ChooseServicesAdapter extends RecyclerView.Adapter<ChooseServicesAdapter.ViewHolder> {
    Context context;
    private String[] trades;
    private int[] images;
    public ChooseServicesAdapter(Context context, String[] trades) {
        this.context = context;
        this.trades = trades;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_items_payment_method,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        holder.ivPortfolioImage.setImageResource(images[position]);
        holder.serviceView.setText(trades[position]);

    }

    @Override
    public int getItemCount() {
        return trades.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView imageView;
        private TextView serviceView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
//            ivPortfolioImage = itemView.findViewById(R.id.service_image);
            serviceView = itemView.findViewById(R.id.service_view_choose_service);
        }
    }
}
