package com.example.barterapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.barterapp.R;
import com.example.barterapp.activities.ChatActivity;
import com.example.barterapp.others.Preferences;
import com.example.barterapp.responses.menu.GetAllUsersNotificationsResponse;
import com.example.barterapp.utils.URLs;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.ViewHolder> {
    Context context;
    private List<GetAllUsersNotificationsResponse.NotificationStreaming> notifications_list;
    private Preferences preferences;

    public NotificationsAdapter(Context context, List<GetAllUsersNotificationsResponse.NotificationStreaming> notifications_list) {
        this.context = context;
        this.notifications_list = notifications_list;
        preferences = new Preferences(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_items_notifications, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(URLs.image_url+notifications_list.get(position).getPicture()).into(holder.ivNotifyUser);
        holder.tvNotifyUserName.setText(notifications_list.get(position).getName());
        holder.tvNotityBody.setText(notifications_list.get(position).getDescription());

        String time = notifications_list.get(position).getCreatedAt();
        String [] date_time = time.split(" ");
        String notfy_time = date_time[1];
        holder.tvNotityTime.setText(notfy_time);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChatActivity.class);
                preferences.setSenderId(notifications_list.get(position).getActorId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return notifications_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView ivNotifyUser;
        private TextView tvNotifyUserName, tvNotityBody, tvNotityTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivNotifyUser = itemView.findViewById(R.id.iv_notify_user);
            tvNotifyUserName = itemView.findViewById(R.id.tv_notify_user_name);
            tvNotityBody = itemView.findViewById(R.id.tv_notify_body);
            tvNotityTime = itemView.findViewById(R.id.tv_notify_time);
        }
    }
}
