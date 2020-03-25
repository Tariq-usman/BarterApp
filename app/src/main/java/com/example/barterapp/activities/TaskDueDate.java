package com.example.barterapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.barterapp.R;
import com.example.barterapp.others.Preferences;
import com.example.barterapp.responses.tasks.AddNewJobResponse;
import com.example.barterapp.utils.URLs;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class TaskDueDate extends AppCompatActivity {
    private Button postBtn;
    private ImageView backBtn;
    private TextView dateView;
    private EditText etAmount;
    private int mYear, mMonth, mDay;
    private LinearLayout dateLayout;
    private String title, description, service_type, latitude, longitude;
    private Preferences preferences;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_due_date);
        customProgressDialog(TaskDueDate.this);

        preferences = new Preferences(this);
        title = getIntent().getStringExtra("title");
        description = getIntent().getStringExtra("description");
        service_type = getIntent().getStringExtra("service_type");
        latitude = getIntent().getStringExtra("lat");
        longitude = getIntent().getStringExtra("long");

        dateView = findViewById(R.id.date_view_add_task);
        dateLayout = findViewById(R.id.date_layout_add_task);
        dateLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Date
                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(TaskDueDate.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                dateView.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.setTitle("Select Date..");
                datePickerDialog.show();
            }
        });

        backBtn = findViewById(R.id.iv_back_service_due_date);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        etAmount = findViewById(R.id.et_estimated_budget);


        postBtn = findViewById(R.id.post_btn_due_date);
        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postNewJob();
            }
        });
    }

    private void postNewJob() {
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(TaskDueDate.this);
        final Gson gson = new GsonBuilder().create();
        StringRequest request = new StringRequest(Request.Method.POST, URLs.add_new_job_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                AddNewJobResponse jobResponse = gson.fromJson(response, AddNewJobResponse.class);
                Toast.makeText(TaskDueDate.this, "Job add successfully."/* + jobResponse.getJobs()*/, Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                Intent intent = new Intent(TaskDueDate.this, MainPage.class);
                intent.putExtra("fragment_status", "add_task");
                startActivity(intent);
                finish();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());
                Toast.makeText(TaskDueDate.this, "Some thing went wrong!", Toast.LENGTH_SHORT).show();
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
                map.put("title", title);
                map.put("description", description);
                String date = dateView.getText().toString().trim();
                /*SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String formated_date = dateFormat.format(date);*/
                map.put("due_date", date);
                map.put("latitude", latitude);
                map.put("longitude", longitude);
                map.put("estimated_budget", etAmount.getText().toString().trim());
                map.put("service_type", service_type);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
