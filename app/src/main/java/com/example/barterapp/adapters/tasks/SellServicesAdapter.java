package com.example.barterapp.adapters.tasks;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barterapp.R;
import com.example.barterapp.activities.HistoryDetails;
import com.example.barterapp.activities.ServicesActivity;
import com.example.barterapp.others.Preferences;

import de.hdodenhof.circleimageview.CircleImageView;

public class SellServicesAdapter extends RecyclerView.Adapter<SellServicesAdapter.ViewHolder> {
    Context context;
    private int[] images;
    private Preferences preferences;
    public SellServicesAdapter(Context context, int[] images) {
        this.context = context;
        this.images = images;
        preferences = new Preferences(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_items_taska,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imageView.setImageResource(images[position]);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences.setFragmentStatus("sell services");
                Intent intent = new Intent(context, HistoryDetails.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.services);
        }
    }
}