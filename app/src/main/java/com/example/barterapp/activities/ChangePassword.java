package com.example.barterapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.example.barterapp.utils.URLs;

import java.util.HashMap;
import java.util.Map;

public class ChangePassword extends AppCompatActivity {
    private ImageView backBtn;
    private Button saveBtn;
    private EditText etOldPass,etNewPass,etConfirmPass;
    private Preferences preferences;
    String oldPassword,newPassword,confirmPassword;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        customProgressDialog(ChangePassword.this);
        preferences = new Preferences(this);
        backBtn = findViewById(R.id.iv_back_change_pass);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        etOldPass = findViewById(R.id.et_old_pass_cp);
        etNewPass = findViewById(R.id.et_new_pass_cp);
        etConfirmPass = findViewById(R.id.et_confirm_pass_cp);
        saveBtn = findViewById(R.id.btn_save_pass);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldPassword = etOldPass.getText().toString().trim();
                newPassword = etNewPass.getText().toString().trim();
                confirmPassword = etConfirmPass.getText().toString().trim();
                if(oldPassword.length()<8)
                {
                    etOldPass.setError("Incorrect password");
                }else if (newPassword.length()<8){
                    etNewPass.setError("Please enter correct password");
                }else if (!confirmPassword.equalsIgnoreCase(newPassword)){
                    etNewPass.setError("Confirm pass does't match");
                }else {
                    changePassword();
                }
            }
        });
    }

    private void changePassword() {
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(ChangePassword.this);

        StringRequest request = new StringRequest(Request.Method.POST, URLs.change_pass_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Toast.makeText(ChangePassword.this, "Password change Successfully..", Toast.LENGTH_SHORT).show();
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error ",error.toString());
                progressDialog.dismiss();

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headerMap = new HashMap<>();
                String token = preferences.getToken();
                headerMap.put("Authorization","Bearer "+token);
                return headerMap;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("old_password",oldPassword);
                map.put("new_password",newPassword);
                return map;
            }
        };
        requestQueue.add(request);
    };
    public void customProgressDialog(Context context){
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
