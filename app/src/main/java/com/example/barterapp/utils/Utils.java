package com.example.barterapp.utils;

import android.app.ProgressDialog;
import android.content.Context;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    Context context;
public static    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    public static  boolean isValidPassword(final String password) {

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

public static final void progressDialog(Context context){
    ProgressDialog progressDialog = new ProgressDialog(context);
    // Setting Message
    progressDialog.setMessage("Loading...");
    // Progress Dialog Style Spinner
    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    // Fetching max value
    progressDialog.getMax();
}

}
