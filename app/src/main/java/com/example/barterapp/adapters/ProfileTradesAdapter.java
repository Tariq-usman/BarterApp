package com.example.barterapp.adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barterapp.R;
import com.example.barterapp.others.Preferences;

import java.util.List;

public class ProfileTradesAdapter extends RecyclerView.Adapter<ProfileTradesAdapter.ViewHolder> {
    Context context;
    List<String> trades;
    private Dialog myDialog;
    private TextView tradesView;
    private Button sendInvoice, btnYes, btnNo;
    String trade_text, trade, edited_trade;
    private int pos = -1;
    int status;
    private Preferences preferences;


    public ProfileTradesAdapter(Context context, List<String> trades) {
        this.context = context;
        this.trades = trades;
        myDialog = new Dialog(context);
        preferences = new Preferences(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_items_profile_trades, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
         status = preferences.getEditStatus();
         Log.i("status",String.valueOf(status));
        if (status==0){
            holder.textView.setEnabled(false);
            holder.iv_delete.setVisibility(View.GONE);
        }else {
            holder.textView.setEnabled(true);
            holder.iv_delete.setVisibility(View.VISIBLE);
        }

        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trade_text = holder.textView.getText().toString();
                pos = position;
                AddTrades();
            }
        });

        if (position == pos) {
            trades.remove(pos);
            trades.add(pos,edited_trade);
            holder.textView.setText(trades.get(position));
        } else {
            holder.textView.setText(trades.get(position));
        }

        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trades.remove(position);
                notifyItemRemoved(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return trades.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private ImageView iv_delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_trade_text);
              iv_delete= itemView.findViewById(R.id.iv_delete_trade);
        }
    }

    public void AddTrades() {

        myDialog.setContentView(R.layout.custom_dialog_edit_trades);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        tradesView = myDialog.findViewById(R.id.et_edit_trades);
        btnYes = myDialog.findViewById(R.id.btn_edit_trade);
        tradesView.setText(trade_text);
        btnYes.setEnabled(true);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trade = tradesView.getText().toString().trim();
                if (!trade.isEmpty() && trade != null && trade != "") {
                    edited_trade = trade;
                    notifyDataSetChanged();
                    notifyItemInserted(pos);
                    myDialog.cancel();
                }
            }
        });
        myDialog.show();
    }
}
