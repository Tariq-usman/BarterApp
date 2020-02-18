package com.example.barterapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.barterapp.R;

public class SignInActivity extends AppCompatActivity {

    private Button logInBtn;
    private EditText etEmail, etPassword;
    private TextView tv,textViewSignUp;
    private String email, pass;
    private LayoutInflater inflater;
    private View layout;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

       generateCustomToast();

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
                if (email.isEmpty() || email == null) {
                    etEmail.setError("Please enter email..");
                } else if (pass.isEmpty() || pass== null) {
                    etPassword.setError("Please enter password..");
                } else {
                    tv.setText("Successfully SignIn");
                    toast.show();
                    startActivity(new Intent(SignInActivity.this, MainPage.class));
                    finish();
                }
            }
        });
    }
    private void generateCustomToast() {
        inflater = getLayoutInflater();
        layout = inflater.inflate(R.layout.custom_toast,null);
        tv = layout.findViewById(R.id.txtvw);
        toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
//        toast.show();
    }
}
