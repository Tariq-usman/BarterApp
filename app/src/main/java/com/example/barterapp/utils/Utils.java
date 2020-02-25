package com.example.barterapp.utils;

import android.app.ProgressDialog;
import android.content.Context;

public class Utils {
    Context context;
public static    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

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
