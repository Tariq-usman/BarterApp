package com.example.barterapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.barterapp.R;

public class SignUpActivity extends AppCompatActivity {
    private TextView textViewSignIn;
    private EditText etName,etEmail,etPass,etConfPass,etCountry,etCity,etZipCode;
    private Button nextBtn;
    private String name,email,pass,cPass,country,city,zCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        etName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
        etPass = findViewById(R.id.et_password);
        etConfPass = findViewById(R.id.et_confirm_pass);
        /*etCountry = findViewById(R.id.et_country);
        etCity= findViewById(R.id.et_city);
        etZipCode= findViewById(R.id.et_zip_code);*/

        nextBtn = findViewById(R.id.btn_next);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name =etName.getText().toString().trim();
                email = etEmail.getText().toString().trim();
                pass=etPass.getText().toString().trim();
                cPass = etConfPass.getText().toString().trim();
                /*country=etCountry.getText().toString().trim();
                city=etCity.getText().toString().trim();
                zCode=etZipCode.getText().toString().trim();*/
                if ( name== null || name.isEmpty()){
                    etName.setError("Please enter name..");
                }else if ( email== null || email.isEmpty()){
                    etEmail.setError("Please enter email..");
                }else if ( pass== null || pass.isEmpty()){
                    etPass.setError("Please enter pass..");
                }else if (cPass== null || cPass.isEmpty()){
                    etConfPass.setError("Please enter confirm pass..");
                }/*else if ( country== null || country.isEmpty()){
                    etCountry.setError("Please enter country name..");
                }else if ( city== null || city.isEmpty()){
                    etCity.setError("Please enter city..");
                }else if ( zCode== null || zCode.isEmpty()){
                    etZipCode.setError("Please enter zip code..");
                }*/else {
                    startActivity(new Intent(SignUpActivity.this, LocationActivity.class));
                    finish();
                }
            }
        });

    }
}
