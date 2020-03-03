package com.example.barterapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.example.barterapp.responses.user.SignUpResponse;
import com.example.barterapp.utils.URLs;
import com.example.barterapp.utils.Utils;
import com.example.barterapp.others.Preferences;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    private TextView tv, textViewSignIn, tvLocation;
    private EditText etName, etEmail, etPass, etConfPass;
    private Button signUpButton;
    private String name, email, pass, cPass,location;
    private Preferences preferences;
    private LayoutInflater inflater;
    private View layout;
    private Toast toast;
    private ImageView selectLocationFromMap;
    Context context;
    Address location_lat_long;
    double latitude,longitude;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        customProgressDialog(SignUpActivity.this);

        etName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
        etPass = findViewById(R.id.et_password);
        etConfPass = findViewById(R.id.et_confirm_pass);
        generateCustomToast();
        tvLocation = findViewById(R.id.tv_location_signup);
        selectLocationFromMap = findViewById(R.id.select_location_from_map);
        selectLocationFromMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, MapsActivity.class));
            }
        });

        signUpButton = findViewById(R.id.btn_signup);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = etName.getText().toString().trim();
                email = etEmail.getText().toString().trim();
                pass = etPass.getText().toString().trim();
                cPass = etConfPass.getText().toString().trim();
                location = tvLocation.getText().toString().trim();
                if (name == null || name.isEmpty()) {
                    etName.setError("Please enter name..");
                } else if (!email.matches(Utils.emailPattern)) {
                    etEmail.setError("Please enter valid email..");
                } else if (pass.length() < 6) {
                    etPass.setError("Password is weak..");
                } else if (!cPass.matches(pass)) {
                    etConfPass.setError("Password Not matching..");
                } else {

                    signUpUser();

                }
            }
        });

        preferences = new Preferences(this);


        textViewSignIn = findViewById(R.id.tv_signin);
        textViewSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
                finish();
            }
        });

    }


    private void getFromLocation(String address)
    {
        Geocoder geocoder = new Geocoder(getBaseContext());
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocationName("Example Street,xyz", 20);
            for(int i = 0; i < addresses.size(); i++) { // MULTIPLE MATCHES
                Address addr = addresses.get(i);
                latitude = addr.getLatitude();
                longitude = addr.getLongitude(); // DO SOMETHING WITH VALUES
            }
        }catch (Exception e){

        }
}



    private void signUpUser() {
        progressDialog.show();
        getFromLocation(location);
        RequestQueue requestQueue = Volley.newRequestQueue(SignUpActivity.this);

        StringRequest request=new StringRequest(Request.Method.POST, URLs.signup_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               // Toast.makeText(SignUpActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                Gson gson =new GsonBuilder().create();
                SignUpResponse signUpResponse = gson.fromJson(response,SignUpResponse.class);
                String message = signUpResponse.getMessage().toString();
                Log.e("Response ", message);
                tv.setText("Register Successfully");
                toast.show();
                progressDialog.dismiss();
                startActivity(new Intent(getApplicationContext(),MainPage.class));
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String msgError = new String(error.networkResponse.data);
                progressDialog.dismiss();
                Log.e("error MSG", msgError);
                Toast.makeText(SignUpActivity.this, "Error "+error.networkResponse.data, Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String > header = new HashMap<>();
                return header;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String > map = new HashMap<>();
                map.put("name",name);
                map.put("email",email);
                map.put("password",pass);
                map.put("latitude",String.valueOf(latitude));
                map.put("longitude",String.valueOf(longitude));
                return map;
            }
        };
        requestQueue.add(request);
    }

    @Override
    protected void onResume() {
        super.onResume();
        tvLocation.setText(preferences.getLocation());
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
