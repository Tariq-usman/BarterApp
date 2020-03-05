package com.example.barterapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barterapp.R;
import com.example.barterapp.others.Preferences;
import com.example.barterapp.responses.chat_responses.GetAllChatMessagesResponse;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    Context context;
    int sender_id;
    List<GetAllChatMessagesResponse.Message> messageList;
    private Preferences  preferences;

    public ChatAdapter(int sender_id, Context applicationContext, List<GetAllChatMessagesResponse.Message> messageList) {
        this.context = applicationContext;
        this.sender_id = sender_id;
        this.messageList = messageList;
        preferences = new Preferences(context);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_items_chat, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       // int user_id = preferences.getUserId();
        int id = messageList.get(position).getSenderId();
        if (sender_id == id){
            holder.layoutReceiver.setVisibility(View.VISIBLE);
            holder.tvReceiver.setText(messageList.get(position).getText());
        }else {
            holder.layoutSender.setVisibility(View.VISIBLE);
            holder.tvSender.setText(messageList.get(position).getText());
        }

    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView ivSender,ivReceiver;
        private LinearLayout layoutSender, layoutReceiver;
        private TextView tvSender, tvReceiver;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivSender = itemView.findViewById(R.id.sender_pic);
            ivReceiver = itemView.findViewById(R.id.receiver_pic);
            tvSender = itemView.findViewById(R.id.sender_text);
            tvReceiver = itemView.findViewById(R.id.receiver_text);
            layoutSender = itemView.findViewById(R.id.sender_layout);
            layoutReceiver = itemView.findViewById(R.id.receiver_layout);


        }
    }
}
