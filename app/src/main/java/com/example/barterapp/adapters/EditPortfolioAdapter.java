package com.example.barterapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barterapp.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditPortfolioAdapter extends RecyclerView.Adapter<EditPortfolioAdapter.ViewHolder> {
    Context context;
    public EditPortfolioAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_items_edit_portfolio,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //holder.imageView.setImageResource(images[position]);

    }

    @Override
    public int getItemCount() {
        return 6;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
          //  imageView = itemView.findViewById(R.id.iv_home_products);
        }
    }
}
