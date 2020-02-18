package com.example.barterapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.barterapp.R;

public class LocationActivity extends AppCompatActivity {
    private TextView tv,textViewSignIn;
    private Button signUpButton;
    private LayoutInflater inflater;
    private View layout;
    private Toast toast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        generateCustomToast();


        signUpButton = findViewById(R.id.btn_signup);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText("Successfully SignUp");
//                Toast.makeText(LocationActivity.this, "SignUp Successfully...", Toast.LENGTH_SHORT).show();
                toast.show();
            }
        });
        textViewSignIn = findViewById(R.id.tv_signin);
        textViewSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LocationActivity.this, SignInActivity.class));
                finish();
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
