package com.example.barterapp.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.barterapp.R;
import com.example.barterapp.interfaces.RecyclerClickInterface;
import com.example.barterapp.others.Preferences;
import com.example.barterapp.responses.profile.CurrentUserResponse;
import com.example.barterapp.utils.URLs;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProfilePortfolioAdapter extends RecyclerView.Adapter<ProfilePortfolioAdapter.ViewHolder> {
    Context context;
    int status;
    private int RESULT_LOAD_IMAGE = 1;
    List<String> portfolio_pics;
    private Preferences preferences;
    RecyclerClickInterface clickInterface;
    int last_position = 0;

    public ProfilePortfolioAdapter(Context context, List<String> portfolio_pics) {
        this.context = context;
        this.portfolio_pics = portfolio_pics;
        preferences = new Preferences(context);
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
            String imsges = portfolio_pics.get(position);
            Glide.with(holder.ivPortfolioImage.getContext()).load(imsges).into(holder.ivPortfolioImage);
        }
        String imsges = URLs.portfolio_images_url + portfolio_pics.get(position);
//        Picasso.get().load(imsges).error(R.drawable.portfolio).into(holder.ivPortfolioImage);
        Glide.with(holder.ivPortfolioImage.getContext()).load(imsges).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                holder.progressBar.setVisibility(View.INVISIBLE);
                return false;
            }
        }).into(holder.ivPortfolioImage);
        holder.ivDeletePortfolio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                portfolio_pics.remove(portfolio_pics.get(position));
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
        return portfolio_pics.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPortfolioImage, ivDeletePortfolio;
        private ProgressBar progressBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPortfolioImage = itemView.findViewById(R.id.ivPortfolio);
            ivDeletePortfolio = itemView.findViewById(R.id.iv_delete_portfolio);
            progressBar = itemView.findViewById(R.id.progress_portfolio);
        }
    }
}
