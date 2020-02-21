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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.barterapp.R;
import com.example.barterapp.utils.Utils;
import com.example.barterapp.others.Preferences;

public class SignUpActivity extends AppCompatActivity {
    private TextView tv,textViewSignIn,tvLocation;
    private EditText etName,etEmail,etPass,etConfPass;
    private Button signUpButton;
    private String name,email,pass,cPass;
    private Preferences preferences;
    private LayoutInflater inflater;
    private View layout;
    private Toast toast;
    private ImageView selectLocationFromMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        etName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
        etPass = findViewById(R.id.et_password);
        etConfPass = findViewById(R.id.et_confirm_pass);
        generateCustomToast();
        tvLocation = findViewById(R.id.tv_location_signup);
//        tvLocation.setMovementMethod(new ScrollingMovementMethod());
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
                name =etName.getText().toString().trim();
                email = etEmail.getText().toString().trim();
                pass=etPass.getText().toString().trim();
                cPass = etConfPass.getText().toString().trim();
                if ( name== null || name.isEmpty()){
                    etName.setError("Please enter name..");
                }else if (!email.matches(Utils.emailPattern)){
                    etEmail.setError("Please enter valid email..");
                }else if ( pass.length()<6){
                    etPass.setError("Password is weak..");
                }else if (!cPass.matches(pass)){
                    etConfPass.setError("Password Not matching..");
                }else {
                    tv.setText("Successfully SignUp");
                    toast.show();
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
    @Override
    protected void onResume() {
        super.onResume();
        tvLocation.setText(preferences.getLocation());
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
