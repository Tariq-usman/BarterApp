package com.example.barterapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.barterapp.R;

import java.util.Calendar;

public class TaskDueDate extends AppCompatActivity {
    private Button postBtn;
    private ImageView backBtn;
    private TextView dateView;
    private int mYear, mMonth, mDay;
    private LinearLayout dateLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_due_date);
        dateView = findViewById(R.id.date_view_add_task);
        dateLayout = findViewById(R.id.date_layout_add_task);
        dateLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Date
                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(TaskDueDate.this,
                        new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dateView.setText(dayOfMonth+"/"+month+1+"/"+year);
                    }
                },mYear,mMonth,mDay);
                datePickerDialog.setTitle("Select Date..");
                datePickerDialog.show();
            }
        });

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
                Toast.makeText(TaskDueDate.this, "Posted..", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
