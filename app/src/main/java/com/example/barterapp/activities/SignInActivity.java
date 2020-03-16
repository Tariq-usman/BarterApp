package com.example.barterapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.barterapp.application.AppClass;
import com.example.barterapp.others.Preferences;
import com.example.barterapp.responses.user.LogInResponse;
import com.example.barterapp.utils.URLs;
import com.example.barterapp.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignInActivity extends AppCompatActivity {

    private Button logInBtn;
    private EditText etEmail, etPassword;
    private TextView tv, textViewSignUp;
    private String email, pass;
    private LayoutInflater inflater;
    private View layout;
    private Toast toast;
    private Preferences preferences;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        preferences = new Preferences(this);
        generateCustomToast();
        customProgressDialog(SignInActivity.this);


        etEmail = findViewById(R.id.et_email_signin);
        etPassword = findViewById(R.id.et_pass_signin);
        textViewSignUp = findViewById(R.id.tv_sign_up);
        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
                finish();

            }
        });
        logInBtn = findViewById(R.id.btn_login);
        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = etEmail.getText().toString().trim();
                pass = etPassword.getText().toString().trim();
                if (!email.matches(Utils.emailPattern)) {
                    etEmail.setError("Please enter valid email..");
                } else if (pass.length()<6) {
                    etPassword.setError("Please enter valid password..");
                } else {
                    logInUser();
                }
            }
        });
    }

    private void logInUser() {
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(SignInActivity.this);
        final Gson gson = new GsonBuilder().create();
        StringRequest request = new StringRequest(Request.Method.POST, URLs.login_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               // Toast.makeText(SignInActivity.this, "Login", Toast.LENGTH_SHORT).show();
                LogInResponse logInResponse =gson.fromJson(response,LogInResponse.class);
                String token = logInResponse.getToken();
                int user_id=logInResponse.getUser().getId();
                preferences.setToken(token);
                preferences.setUserId(user_id);
                tv.setText("Successfully SignIn");
                toast.show();
                updateDeviceToken();
                progressDialog.dismiss();
                startActivity(new Intent(SignInActivity.this, MainPage.class));
                finish();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(SignInActivity.this, "User not found!", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String ,String> map = new HashMap<>();
                map.put("email",email);
                map.put("password",pass);
                return map;
            }
        };
        requestQueue.add(request);
    }
    private void updateDeviceToken() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        Gson gson = new GsonBuilder().create();
        StringRequest request = new StringRequest(Request.Method.POST, URLs.update_device_token_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SignInActivity.this, "Error", Toast.LENGTH_SHORT).show();
                Log.e("App class", error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headerMap = new HashMap<>();
                headerMap.put("Authorization", "Bearer " + preferences.getToken());
                return headerMap;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("device_token", preferences.getDeviceToken());
                return map;
            }
        };
        requestQueue.add(request);
    }

    public boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;

      /*  # a digit must occur at least once
          # a lower case letter must occur at least once
          # an upper case letter must occur at least once
          # a special character must occur at least once you can replace with your special characters
          # no whitespace allowed in the entire string
          # anything, at least six places though
          # end-of-string*/
        final String PASSWORD_PATTERN = "^" +
                "(?=.*[0-9])" +
                "(?=.*[a-z])" + //a lower case letter must occur at least once
                "(?=.*[A-Z])" + //an upper case letter must occur at least once
                "(?=.*[@#$%^&+=])" +
                "(?=\\S+$).{4,}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

    private void generateCustomToast() {
        inflater = getLayoutInflater();
        layout = inflater.inflate(R.layout.custom_toast, null);
        tv = layout.findViewById(R.id.txtvw);
        toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
//        toast.show();
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
