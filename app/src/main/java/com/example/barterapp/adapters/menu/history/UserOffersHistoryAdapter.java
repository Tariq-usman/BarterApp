package com.example.barterapp.adapters.menu.history;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.barterapp.R;
import com.example.barterapp.activities.SellServicesDetails;
import com.example.barterapp.others.Preferences;
import com.example.barterapp.responses.menu.AllUserJobsHistoryResponse;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserOffersHistoryAdapter extends RecyclerView.Adapter<UserOffersHistoryAdapter.ViewHolder> {
    Context context;
    List<AllUserJobsHistoryResponse.Offer> userHistoryOffers;
    private Preferences preferences;
    long daysDiff;

    public UserOffersHistoryAdapter(Context context, List<AllUserJobsHistoryResponse.Offer> userHistoryOffers) {
        this.context = context;
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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (userHistoryOffers.get(position).getJob() != null) {
            Glide.with(context).load(userHistoryOffers.get(position).getUser().getPicture());
            holder.tvTitle.setText(userHistoryOffers.get(position).getJob().getTitle());
            holder.tvPrice.setText(userHistoryOffers.get(position).getJob().getEstimatedBudget().toString());
            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String current_date = df.format(c);
            String due_date = userHistoryOffers.get(position).getJob().getDueDate();
            daybetween(current_date/*"25/02/2020"*/, due_date /*"28/02/2020"*/, "yyyy-MM-dd");

            holder.tvDuration.setText(daysDiff + "Days");
            Geocoder geocoder = new Geocoder(context, Locale.ENGLISH);
            List<Address> addresses;
            Double lat = Double.parseDouble(userHistoryOffers.get(position).getUser().getLatitude());
            Double lng = Double.parseDouble(userHistoryOffers.get(position).getUser().getLongitude());

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
        } else {
            Log.e("error", "No job available..");
        }

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
        return userHistoryOffers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imageView;
        private TextView tvTitle, tvPrice, tvDuration, tvLocation, tvViewJob;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_my_task_products_history);
            tvTitle = itemView.findViewById(R.id.title_history);
            tvPrice = itemView.findViewById(R.id.price_history);
            tvDuration = itemView.findViewById(R.id.duration_history);
            tvLocation = itemView.findViewById(R.id.location_history);
            tvViewJob = itemView.findViewById(R.id.view_job_history);
        }
    }
}
