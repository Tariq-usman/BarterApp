package com.example.barterapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.barterapp.R;
import com.example.barterapp.utils.Utils;

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


//    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

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
                if (!email.matches(Utils.emailPattern)) {
                    etEmail.setError("Please enter valid email..");
                } else if (pass.length()<6) {
                    etPassword.setError("Please enter valid password..");
                } else {
                    tv.setText("Successfully SignIn");
                    toast.show();
                    startActivity(new Intent(SignInActivity.this, MainPage.class));
                    finish();
                }
            }
        });
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
}
