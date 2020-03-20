package com.example.barterapp.adapters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.barterapp.R;
import com.example.barterapp.activities.MainPage;
import com.example.barterapp.others.Preferences;
import com.example.barterapp.responses.chat_responses.GetAllUserOfferResponse;
import com.example.barterapp.utils.URLs;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AcceptRejectOfferAdapter extends RecyclerView.Adapter<AcceptRejectOfferAdapter.ViewHolder> {
    private Context context;
    private FlexboxLayoutManager layoutManager;
    private FlexboxLayoutManager fLayoutManager;
    private int offer_id;
    private Preferences preferences;
    private ProgressDialog progressDialog;
    private List<GetAllUserOfferResponse.Offer> offerList;
    List<String> trades_list;
    List<String> returnTradesList;

    public AcceptRejectOfferAdapter(Context context, List<GetAllUserOfferResponse.Offer> offerList) {
        this.context = context;
        preferences = new Preferences(context);
        customProgressDialog(context);
        this.offerList = offerList;
        trades_list = new ArrayList<>();
        returnTradesList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_items_custom_offers, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (!offerList.isEmpty()) {
            if (offerList.get(position).getOfferTypeId() ==1){
                holder.layoutPrice.setVisibility(View.GONE);
            }else {
                holder.layoutReturnTrades.setVisibility(View.GONE);
            }
            holder.tvPrice.setText(offerList.get(position).getOffer());
            holder.tvDueDate.setText(offerList.get(position).getJob().getDueDate());
            holder.tvSecurityAmount.setText(String.valueOf(offerList.get(position).getBarterSecurity()));
            holder.tvDescription.setText(offerList.get(position).getDescription());

            String trades = offerList.get(position).getUser().getTrades();
            String return_trades = offerList.get(position).getOffer();

            //Recycler for trades
            layoutManager = new FlexboxLayoutManager(context);
            layoutManager.setFlexWrap(FlexWrap.WRAP);
            layoutManager.setAlignItems(AlignItems.STRETCH);
            layoutManager.setFlexDirection(FlexDirection.ROW);
            layoutManager.setJustifyContent(JustifyContent.FLEX_START);
            holder.recyclerViewTrades.setLayoutManager(layoutManager);
            trades_list = Arrays.asList(trades.replaceAll("\\s", " ").split(","));
            holder.recyclerViewTrades.setAdapter(new UserOffersTradesAdapter(context, trades_list));

            //Recycler for return trades
            fLayoutManager = new FlexboxLayoutManager(context);
            fLayoutManager.setFlexWrap(FlexWrap.WRAP);
            fLayoutManager.setAlignItems(AlignItems.STRETCH);
            fLayoutManager.setFlexDirection(FlexDirection.ROW);
            fLayoutManager.setJustifyContent(JustifyContent.FLEX_START);
            holder.recyclerViewReturnTrades.setLayoutManager(fLayoutManager);
            returnTradesList = Arrays.asList(return_trades.replaceAll("\\s", " ").split(","));
        holder.recyclerViewReturnTrades.setAdapter(new UserOffersReturnTradesAdapter(context, returnTradesList));
        } else {
            Toast.makeText(context, "No data found!", Toast.LENGTH_SHORT).show();
        }

        holder.acceptOfferBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                offer_id = offerList.get(position).getId();
                acceptOffer();
            }
        });
        holder.rejectOfferBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                offer_id = offerList.get(position).getId();
                rejectOffer();
            }
        });
    }

    private void rejectOffer() {
        progressDialog.show();

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(Request.Method.GET, URLs.reject_offer_url + offer_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Toast.makeText(context, "response", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, MainPage.class);
//                intent.putExtra("fragment_status", "home");
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
        };
        requestQueue.add(request);
    }

    private void acceptOffer() {
        progressDialog.show();

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
                Log.e("Accept Error", error.toString());
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
        private Button acceptOfferBtn, rejectOfferBtn;
        private TextView tvPrice, tvDueDate, tvSecurityAmount, tvDescription;
        private RecyclerView recyclerViewReturnTrades, recyclerViewTrades;
        private LinearLayout layoutPrice,layoutReturnTrades;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutPrice = itemView.findViewById(R.id.layou_price);
            layoutReturnTrades = itemView.findViewById(R.id.layout_return_service);
            acceptOfferBtn = itemView.findViewById(R.id.accept_offer_btn);
            rejectOfferBtn = itemView.findViewById(R.id.reject_offer_btn);
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
