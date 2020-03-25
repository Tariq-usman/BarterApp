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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeleteProfilePortfolioAdapter extends RecyclerView.Adapter<DeleteProfilePortfolioAdapter.ViewHolder> {
    Context context;
    int status;
    List<CurrentUserResponse.User.Portfolio> portfolio_pics;
    private Preferences preferences;
    RecyclerClickInterface clickInterface;
    int last_position = 0;
    Integer id;
    public DeleteProfilePortfolioAdapter(Context context, List<CurrentUserResponse.User.Portfolio> portfolio_pics) {
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
        {
            holder.ivPortfolioImage.setEnabled(true);
            holder.ivDeletePortfolio.setVisibility(View.VISIBLE);
        }
//        Picasso.get().load(portfolio_pics.get(position)).into(holder.ivPortfolioImage);
        String imsges = URLs.portfolio_images_url +portfolio_pics.get(position).getPicture();
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
                 id = portfolio_pics.get(position).getId();
                deletePortfolio(id,position);
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
    private void deletePortfolio(int portfolio_id, final int position) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        final Gson gson = new GsonBuilder().create();
        StringRequest request = new StringRequest(Request.Method.GET, URLs.delete_portfolio_url + portfolio_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                portfolio_pics.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, portfolio_pics.size());
                Log.e("RESPONSE ", response);
                Toast.makeText(context, "Portfolio delete successfully", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headerMap = new HashMap<>();
                String token = preferences.getToken();
                headerMap.put("Authorization", "Bearer " + token);
                headerMap.put("Accept", "application/json");
                headerMap.put("Content-Type", "application/json");
                return headerMap;
            }

        };
        requestQueue.add(request);
    }

}
