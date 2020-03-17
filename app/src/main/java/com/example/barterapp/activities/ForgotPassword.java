package com.example.barterapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.barterapp.R;

public class ForgotPassword extends AppCompatActivity {
    private TextView tvRememberPass;
    private EditText etEmail;
    private Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        etEmail = findViewById(R.id.et_email_forgot_pass);
        tvRememberPass = findViewById(R.id.tv_remember_pass);
        tvRememberPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ForgotPassword.this, SignInActivity.class));
                finish();
            }
        });

        submitBtn = findViewById(R.id.btn_submit_email);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
