package com.example.barterapp.adapters.menu.history;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.barterapp.activities.SellServicesDetails;
import com.example.barterapp.R;
import com.example.barterapp.others.Preferences;
import com.example.barterapp.responses.menu.AllUserJobsHistoryResponse;
import com.example.barterapp.utils.URLs;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserJobsHistoryAdapter extends RecyclerView.Adapter<UserJobsHistoryAdapter.ViewHolder> {
    Context context;
    List<AllUserJobsHistoryResponse.UserJob> userJobs;
    List<AllUserJobsHistoryResponse.Offer> userHistoryOffers;
    private Preferences preferences;
    long daysDiff;
    Integer user_id;

    public UserJobsHistoryAdapter(Context context, List<AllUserJobsHistoryResponse.UserJob> userHistoryJobs,
                                  List<AllUserJobsHistoryResponse.Offer> userHistoryOffers) {
        this.context = context;
        this.userJobs = userHistoryJobs;
        this.userHistoryOffers = userHistoryOffers;
        preferences = new Preferences(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_items_my_tasks, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Glide.with(context).load(userJobs.get(position).getUser().getPicture());
        holder.tvTitle.setText(userJobs.get(position).getTitle());
        holder.tvPrice.setText(userJobs.get(position).getEstimatedBudget().toString());
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String current_date = df.format(c);
        String due_date = userJobs.get(position).getDueDate();
        daybetween(current_date/*"25/02/2020"*/,due_date /*"28/02/2020"*/, "yyyy-MM-dd");

        holder.tvDuration.setText(daysDiff + "Days");
        Geocoder geocoder = new Geocoder(context, Locale.ENGLISH);
        List<Address> addresses;
        Double lat = Double.parseDouble(userJobs.get(position).getUser().getLatitude());
        Double lng = Double.parseDouble(userJobs.get(position).getUser().getLongitude());

        try {
            addresses = geocoder.getFromLocation(28.963400, 77.711990, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName();
            holder.tvLocation.setText(city);
        } catch (IOException e) {
            e.printStackTrace();
        }
        holder.tvViewJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences.setFragmentStatus("history");
                Intent intent = new Intent(context, SellServicesDetails.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        holder.ivDeleteJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, ""+position, Toast.LENGTH_SHORT).show();
                user_id = userJobs.get(position).getUserId();
                deleteJob(position);
            }
        });

    }

    private void deleteJob(final int position) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        final Gson gson = new GsonBuilder().create();
        StringRequest request = new StringRequest(Request.Method.GET, URLs.delete_user_job_url+ user_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                userJobs.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, userJobs.size());
                Log.e("RESPONSE " , response);
                Toast.makeText(context, "Item delete Successfully", Toast.LENGTH_SHORT).show();
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

    public long daybetween(String date1, String date2, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.ENGLISH);
        Date Date1 = null, Date2 = null;
        try {
            Date1 = sdf.parse(date1);
            Date2 = sdf.parse(date2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        daysDiff = (Date2.getTime() - Date1.getTime()) / (24 * 60 * 60 * 1000);
        return (Date2.getTime() - Date1.getTime()) / (24 * 60 * 60 * 1000);
    }


    @Override
    public int getItemCount() {
        return userJobs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imageView;
        private ImageView ivDeleteJob;
        private TextView tvTitle, tvPrice, tvDuration, tvLocation, tvViewJob;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivDeleteJob = itemView.findViewById(R.id.delete_job_history);
            imageView = itemView.findViewById(R.id.iv_my_task_products_history);
            tvTitle = itemView.findViewById(R.id.title_history);
            tvPrice = itemView.findViewById(R.id.price_history);
            tvDuration = itemView.findViewById(R.id.duration_history);
            tvLocation = itemView.findViewById(R.id.location_history);
            tvViewJob = itemView.findViewById(R.id.view_job_history);
        }
    }
}
