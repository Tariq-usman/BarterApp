package com.example.barterapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barterapp.R;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    Context context;
    private String[] trades = {"Design", "Mobile App Design", "Web Design", "abc", "abc", "Design"};
    private String[] returnTrades = {"Design", "Mobile App Design"};
    private FlexboxLayoutManager layoutManager;
    private FlexboxLayoutManager fLayoutManager;

    public ChatAdapter(Context applicationContext) {
        this.context = applicationContext;
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
        //holder.ivPortfolioImage.setImageResource(images[position]);
        /*holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChatActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });*/
        //Recycler for trades
        layoutManager = new FlexboxLayoutManager(context);
        layoutManager.setFlexWrap(FlexWrap.WRAP);
        layoutManager.setAlignItems(AlignItems.STRETCH);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        holder.recyclerViewTrades.setLayoutManager(layoutManager);
        holder.recyclerViewTrades.setAdapter(new TradesAdapter(context, trades));

        //Recycler for return trades
        fLayoutManager = new FlexboxLayoutManager(context);
        fLayoutManager.setFlexWrap(FlexWrap.WRAP);
        fLayoutManager.setAlignItems(AlignItems.STRETCH);
        fLayoutManager.setFlexDirection(FlexDirection.ROW);
        fLayoutManager.setJustifyContent(JustifyContent.FLEX_START);
        holder.recyclerViewReturnTrades.setLayoutManager(fLayoutManager);
        holder.recyclerViewReturnTrades.setAdapter(new ReturnTradesAdapter(context, returnTrades));

    }

    @Override
    public int getItemCount() {
        return 6;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imageView;
        private RecyclerView recyclerViewReturnTrades, recyclerViewTrades;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //  ivPortfolioImage = itemView.findViewById(R.id.iv_home_products);


            recyclerViewTrades = itemView.findViewById(R.id.recycler_view_chat_invoice_trades);
            recyclerViewReturnTrades = itemView.findViewById(R.id.recycler_view_chat_invoice_return_trades);

        }
    }
}
