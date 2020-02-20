package com.example.barterapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barterapp.R;
import com.example.barterapp.RecyclerClickInterface;
import com.example.barterapp.others.Preferences;

import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.example.barterapp.fragments.Profile.portfolio_pics;

public class ProfilePortfolioAdapter extends RecyclerView.Adapter<ProfilePortfolioAdapter.ViewHolder> {
    Context context;
    int status;
    private int RESULT_LOAD_IMAGE = 1;
    List<Uri> images;
    private Preferences preferences;
    RecyclerClickInterface clickInterface;
    int last_position = 0;

    public ProfilePortfolioAdapter(Context context, List<Uri> images, RecyclerClickInterface recyclerClickInterface) {
        this.context = context;
        this.images = images;
        preferences = new Preferences(context);
        this.clickInterface = recyclerClickInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_items_profile_portfolio, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
/*
        if (images.size() == 0) {
            holder.ivPortfolioImage.setImageResource(R.drawable.notification_image);
            return;
        }*/

        status = preferences.getEditStatus();
        Log.i("status", String.valueOf(status));
        if (status == 0) {
            holder.ivPortfolioImage.setEnabled(false);
            holder.ivDeletePortfolio.setVisibility(View.GONE);
        } else {
            holder.ivPortfolioImage.setEnabled(true);
            holder.ivDeletePortfolio.setVisibility(View.VISIBLE);
        }
        holder.ivPortfolioImage.setImageURI(images.get(position));
        holder.ivDeletePortfolio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                images.remove(images.get(position));
                notifyItemRemoved(position);
                notifyDataSetChanged();
            }
        });
        holder.ivPortfolioImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickInterface.interfaceOnClick(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return images.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPortfolioImage, ivDeletePortfolio;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPortfolioImage = itemView.findViewById(R.id.ivPortfolio);
            ivDeletePortfolio = itemView.findViewById(R.id.iv_delete_portfolio);
        }
    }
}
