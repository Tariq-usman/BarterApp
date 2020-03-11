package com.example.barterapp.adapters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.example.barterapp.R;
import com.example.barterapp.activities.ChatActivity;
import com.example.barterapp.activities.MainPage;
import com.example.barterapp.others.Preferences;
import com.example.barterapp.responses.chat_responses.GetAllUserOfferResponse;
import com.example.barterapp.utils.URLs;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AcceptRejectOfferAdapter extends RecyclerView.Adapter<AcceptRejectOfferAdapter.ViewHolder> {
    private Context context;
    private String[] trades = {"Design", "Mobile App Design", "Web Design", "abc", "abc", "Design"};
    private String[] returnTrades = {"Design", "Mobile App Design"};
    private FlexboxLayoutManager layoutManager;
    private FlexboxLayoutManager fLayoutManager;
    private String offer_id;
    private Preferences preferences;
    private ProgressDialog progressDialog;
    private List<GetAllUserOfferResponse.Offer> offerList;

    public AcceptRejectOfferAdapter(Context context, List<GetAllUserOfferResponse.Offer> offerList) {
        this.context = context;
        preferences = new Preferences(context);
        customProgressDialog(context);
        this.offerList = offerList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_items_custom_offers, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (!offerList.isEmpty()) {
            holder.tvPrice.setText(offerList.get(position).getOffer());
            holder.tvDueDate.setText(offerList.get(position).getJob().getDueDate());
            holder.tvSecurityAmount.setText(String.valueOf(offerList.get(position).getBarterSecurity()));
            holder.tvDescription.setText(offerList.get(position).getDescription());
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
//        holder.recyclerViewReturnTrades.setAdapter(new ReturnTradesAdapter(context, returnTrades));
        }else {
            Toast.makeText(context, "No data found!", Toast.LENGTH_SHORT).show();
        }

        holder.acceptOfferBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acceptOffer();
            }
        });

    }

    private void acceptOffer() {
        progressDialog.show();
        offer_id = preferences.getOfferId();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(Request.Method.POST, URLs.accept_order_url + offer_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Toast.makeText(context, "response", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, MainPage.class);
                intent.putExtra("fragment_status", "tasks");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                ((Activity) context).finish();
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());
                Toast.makeText(context, "error", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headerMap = new HashMap<>();
                String token = preferences.getToken();
                headerMap.put("Authorization", "Bearer " + token);
                return headerMap;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("status_id", "3");
                return map;
            }
        };
        requestQueue.add(request);
    }

    @Override
    public int getItemCount() {
        return offerList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private Button acceptOfferBtn;
        private TextView tvPrice, tvDueDate, tvSecurityAmount, tvDescription;
        private RecyclerView recyclerViewReturnTrades, recyclerViewTrades;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            acceptOfferBtn = itemView.findViewById(R.id.accept_offer_btn);
            recyclerViewTrades = itemView.findViewById(R.id.recycler_view_chat_invoice_trades);
            recyclerViewReturnTrades = itemView.findViewById(R.id.recycler_view_chat_invoice_return_trades);
            tvPrice = itemView.findViewById(R.id.tvPrice_custom_offer);
            tvDueDate = itemView.findViewById(R.id.tvDate_custom_offer);
            tvSecurityAmount = itemView.findViewById(R.id.tvSecurity_amount_custom_offer);
            tvDescription = itemView.findViewById(R.id.tvDescription_custom_offer);
        }
    }

    public void customProgressDialog(Context context) {
        progressDialog = new ProgressDialog(context);
        // Setting Message
        progressDialog.setMessage("Loading...");
        // Progress Dialog Style Spinner
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // Fetching max value
        progressDialog.getMax();
    }
}
