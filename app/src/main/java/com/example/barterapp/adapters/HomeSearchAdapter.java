package com.example.barterapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barterapp.R;
import com.example.barterapp.activities.MakeOffer;
import com.example.barterapp.responses.home.GetAllJobsResponse;
import com.example.barterapp.responses.home.SearchJobResponse;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeSearchAdapter extends RecyclerView.Adapter<HomeSearchAdapter.ViewHolder> {
    Context context;
    List<SearchJobResponse.Job> jobs;
    long daysDiff;

    public HomeSearchAdapter(Context context, List<SearchJobResponse.Job> jobs) {
        this.context = context;
        this.jobs = jobs;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_items_home, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final SearchJobResponse.Job jobsResponse = jobs.get(position);

        holder.tvTitle.setText(jobsResponse.getTitle());
        holder.tvPrice.setText("$ "+jobsResponse.getEstimatedBudget().toString());

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String current_date = df.format(c);
        String due_date = jobsResponse.getDueDate();

        daybetween(current_date/*"25/02/2020"*/, due_date /*"28/02/2020"*/, "yyyy-MM-dd");
        if (daysDiff <= 0) {
            holder.tvDuration.setText("0 Days");
        } else {
            holder.tvDuration.setText(daysDiff + " Days");
        }
        Geocoder geocoder = new Geocoder(context, Locale.ENGLISH);
        List<Address> addresses;
        Double lat = Double.parseDouble(jobsResponse.getLatitude());
        Double lng = Double.parseDouble(jobsResponse.getLongitude());

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


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MakeOffer.class);
                intent.putExtra("title",jobsResponse.getTitle());
                intent.putExtra("description",jobsResponse.getDescription());
                intent.putExtra("posted_by",jobsResponse.getUser().getName());
                intent.putExtra("picture",jobsResponse.getUser().getPicture());
                intent.putExtra("trades",jobsResponse.getUser().getTrades());
                intent.putExtra("location",holder.tvLocation.getText().toString());
//                intent.putExtra("longitude",jobsResponse.getLongitude());
                intent.putExtra("due_date",jobsResponse.getDueDate());
                intent.putExtra("budget",jobsResponse.getEstimatedBudget());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

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
        return jobs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imageView;
        private TextView tvTitle, tvPrice, tvDuration, tvLocation;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_home_products);
            tvTitle = itemView.findViewById(R.id.tv_title_home);
            tvPrice = itemView.findViewById(R.id.tv_price_home);
            tvDuration = itemView.findViewById(R.id.tv_duration_home);
            tvLocation = itemView.findViewById(R.id.tv_location_home);
        }
    }
}
