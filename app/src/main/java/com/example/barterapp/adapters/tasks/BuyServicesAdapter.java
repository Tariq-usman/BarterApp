package com.example.barterapp.adapters.tasks;

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

import com.bumptech.glide.Glide;
import com.example.barterapp.R;
import com.example.barterapp.activities.SellServicesDetails;
import com.example.barterapp.others.Preferences;
import com.example.barterapp.responses.tasks.BuySellServicesResponse;
import com.example.barterapp.utils.URLs;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class BuyServicesAdapter extends RecyclerView.Adapter<BuyServicesAdapter.ViewHolder> {
    Context context;
    private List<BuySellServicesResponse.BuyJob> buyJobList;
    private Preferences preferences;

    public BuyServicesAdapter(Context context, List<BuySellServicesResponse.BuyJob> buyJobList) {
        this.context = context;
        this.buyJobList = buyJobList;
        preferences = new Preferences(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_items_buy_sell_services, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Glide.with(context).load(URLs.image_url + buyJobList.get(position).getUser().getPicture()).into(holder.ivUser);
        holder.tvTitle.setText(buyJobList.get(position).getJob().getTitle());
        holder.tvprice.setText("$ " + buyJobList.get(position).getJob().getEstimatedBudget());
        holder.tvSecurity.setText("$ " + buyJobList.get(position).getBuyerSecurity());
        holder.tvDueDate.setText(buyJobList.get(position).getJob().getDueDate());
        Geocoder geocoder = new Geocoder(context, Locale.ENGLISH);
        List<Address> addresses;
        Double lat = Double.parseDouble(buyJobList.get(position).getJob().getLatitude());
        Double lng = Double.parseDouble(buyJobList.get(position).getJob().getLongitude());
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
                preferences.setFragmentStatus("sell services");
                Intent intent = new Intent(context, SellServicesDetails.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        int status_id = buyJobList.get(position).getJob().getStatusId();
        if (status_id == 3) {
            holder.tvStatusText.setText("In Progress");
            holder.viewJobStatus.setBackgroundResource(R.drawable.bg_online_status);
        } else if (status_id == 2) {
            holder.tvStatusText.setText("Completed");
            holder.viewJobStatus.setBackgroundResource(R.drawable.ic_bg_complete_job);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences.setFragmentStatus("sell services");
                Intent intent = new Intent(context, SellServicesDetails.class);
                intent.putExtra("title_my_job", buyJobList.get(position).getJob().getTitle());
                intent.putExtra("description_my_job", buyJobList.get(position).getJob().getDescription());
                intent.putExtra("posted_by_my_job", buyJobList.get(position).getUser().getName());
                intent.putExtra("picture_my_job", buyJobList.get(position).getUser().getPicture());
                intent.putExtra("trades_my_job", buyJobList.get(position).getUser().getTrades());
                intent.putExtra("location_my_job", holder.tvLocation.getText().toString());
                intent.putExtra("duration_my_job", buyJobList.get(position).getJob().getDueDate());
                intent.putExtra("due_date_my_job", buyJobList.get(position).getJob().getDueDate());
                intent.putExtra("budget_my_job", buyJobList.get(position).getJob().getEstimatedBudget());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return buyJobList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView ivUser;
        private TextView tvTitle, tvprice, tvSecurity, tvDueDate, tvLocation, tvStatusText;
        private View viewJobStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivUser = itemView.findViewById(R.id.ivUser_buy_sell_services);
            tvTitle = itemView.findViewById(R.id.tvTitle_buy_sell_services);
            tvprice = itemView.findViewById(R.id.tvPrice_buy_sell_services);
            tvSecurity = itemView.findViewById(R.id.tvBarter_security_buy_sell_services);
            tvDueDate = itemView.findViewById(R.id.tvDue_date_buy_sell_services);
            tvLocation = itemView.findViewById(R.id.tvLocation_buy_sell_services);
            tvStatusText = itemView.findViewById(R.id.tvJob_status_buy_sell_services);
            viewJobStatus = itemView.findViewById(R.id.view_job_status_buy_sell_services);
        }
    }
}
