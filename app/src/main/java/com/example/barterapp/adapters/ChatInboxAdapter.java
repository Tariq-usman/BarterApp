package com.example.barterapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barterapp.activities.ChatActivity;
import com.example.barterapp.R;
import com.example.barterapp.responses.chat_responses.GetAllInboxMessagesResponse;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatInboxAdapter extends RecyclerView.Adapter<ChatInboxAdapter.ViewHolder> {
    Context context;
    List<GetAllInboxMessagesResponse.GetMessage> allInboxMessagesList;
    long different, elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds;
    SimpleDateFormat simpleDateFormat;
    private Date current_date, currentDate, startDate;

    public ChatInboxAdapter(Context context, List<GetAllInboxMessagesResponse.GetMessage> allInboxMessagesList) {
        this.context = context;
        this.allInboxMessagesList = allInboxMessagesList;
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_items_inbox, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tvUserName.setText(allInboxMessagesList.get(position).getName());
        if (allInboxMessagesList.get(position).getLastMessages() != null) {
            holder.tvMessage.setText(allInboxMessagesList.get(position).getLastMessages().getMessages());
            String time = allInboxMessagesList.get(position).getLastMessages().getDateTime();
            String[] date = time.split(" ");
            String s_date = date[1];
            String currentDateAndTime = simpleDateFormat.format(new Date());
            String[] edate = currentDateAndTime.split(" ");
            String e_date = edate[1];
            try {
                currentDate = simpleDateFormat.parse(currentDateAndTime);
                startDate = simpleDateFormat.parse(time);

                printDifference(currentDate, startDate);
            } catch (Exception e) {
                Log.e("Error", e.toString());
            }
                holder.tvTime.setText(elapsedHours + " hours ago");
        }

        //holder.ivPortfolioImage.setImageResource(images[position]);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChatActivity.class);
                Integer id= allInboxMessagesList.get(position).getId();
                intent.putExtra("sender_id",id);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    private void printDifference(Date startDate, Date currentDate) {
        different = currentDate.getTime() - startDate.getTime();
        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        elapsedSeconds = different / secondsInMilli;

        /*System.out.printf(
                "%d days, %d hours, %d minutes, %d seconds%n",
                elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds);*/
    }

    @Override
    public int getItemCount() {
        return allInboxMessagesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView userImage;
        private TextView tvUserName, tvMessage, tvTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userImage = itemView.findViewById(R.id.iv_chat_user);
            tvUserName = itemView.findViewById(R.id.tv_user_name_inbox);
            tvMessage = itemView.findViewById(R.id.tv_message_inbox);
            tvTime = itemView.findViewById(R.id.tv_time_inbox);
        }
    }
}
