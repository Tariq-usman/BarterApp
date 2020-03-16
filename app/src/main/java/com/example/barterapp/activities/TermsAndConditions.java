package com.example.barterapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.barterapp.R;
import com.example.barterapp.responses.menu.TermAndConditionsResponse;
import com.example.barterapp.utils.URLs;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class TermsAndConditions extends AppCompatActivity {
    private Button disAgreeBtn;
    private WebView webView;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_conditions);
        customProgressDialog(TermsAndConditions.this);

        webView = findViewById(R.id.webViewLoginterms);
        getTermAndConditions();
        disAgreeBtn = findViewById(R.id.btn_disagree);
        disAgreeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getTermAndConditions() {
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(TermsAndConditions.this);
        final Gson gson = new GsonBuilder().create();

        StringRequest request = new StringRequest(Request.Method.GET, URLs.terms_conditions_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                TermAndConditionsResponse conditionsResponse = gson.fromJson(response,TermAndConditionsResponse.class);
                String str = conditionsResponse.getGetTerm().get(0).getContent();
                webView.loadData(str, "text/html", "UTF-8");
                Toast.makeText(TermsAndConditions.this, "Response", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error",error.toString());
                progressDialog.dismiss();
            }
        });
        requestQueue.add(request);
    }
    public void customProgressDialog(Context context){
        progressDialog = new ProgressDialog(context);
        // Setting Message
        progressDialog.setMessage("Loading...");
        // Progress Dialog Style Spinner
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // Fetching max value
        progressDialog.getMax();
    }
}
