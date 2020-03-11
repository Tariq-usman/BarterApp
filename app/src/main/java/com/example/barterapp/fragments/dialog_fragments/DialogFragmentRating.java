package com.example.barterapp.fragments.dialog_fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.barterapp.R;
import com.example.barterapp.others.Preferences;
import com.example.barterapp.responses.tasks.RatingResponse;
import com.example.barterapp.utils.URLs;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

public class DialogFragmentRating extends DialogFragment {
    private Preferences preferences;
    private EditText etReview;
    private ImageView ivColseBtn;
    private RatingBar ratingBar;
    private Button submitBtn;
    private ProgressDialog progressDialog;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_framgment_rating, container, false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        preferences = new Preferences(getContext());
        customProgressDialog(getContext());

        ivColseBtn = view.findViewById(R.id.cancel_btn_rate_order);
        ivColseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        ratingBar = view.findViewById(R.id.rating_bar);
        etReview = view.findViewById(R.id.etReview_rate_order);
        submitBtn = view.findViewById(R.id.btnSubmit_rate_order);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submintRatingReview();
            }
        });
        return view;
    }

    private void submintRatingReview() {
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        Gson gson = new GsonBuilder().create();
        StringRequest request = new StringRequest(Request.Method.POST, URLs.reting_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                RatingResponse ratingResponse = gson.fromJson(response, RatingResponse.class);
                Toast.makeText(getContext(), "" + ratingResponse.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());
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
                    int order_id = preferences.getOrderId();
                    map.put("order_id", String.valueOf(order_id));
                    String review = etReview.getText().toString().trim();
                    map.put("review", review);
                    double rating = ratingBar.getRating();
                    map.put("rating", String.valueOf(rating));
                    return map;
                }
        };
        requestQueue.add(request);
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
