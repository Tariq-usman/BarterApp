package com.example.barterapp.activities.tasks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.barterapp.R;

public class ServiceDueDate extends AppCompatActivity {
    private Button postBtn;
    private ImageView backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_due_date);

        backBtn = findViewById(R.id.iv_back_service_due_date);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        postBtn = findViewById(R.id.post_btn_due_date);
        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ServiceDueDate.this, "Posted..", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
