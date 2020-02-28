package com.example.barterapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.barterapp.R;

public class AddTask extends AppCompatActivity {

    private Button nextBtn;
    private ImageView backBtn;
    private EditText etTitle, etDescription;
    private TextView tvLocation;
    private RadioButton rbPhysical, rbOnline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        backBtn = findViewById(R.id.iv_back_add_service);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        etTitle = findViewById(R.id.et_service_title);
        etDescription = findViewById(R.id.et_service_description);
        rbPhysical = findViewById(R.id.rb_physical);
        rbOnline = findViewById(R.id.rb_online);

        nextBtn = findViewById(R.id.next_btn_add_service);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddTask.this,TaskDueDate.class);
                intent.putExtra("title",etTitle.getText().toString().trim());
                intent.putExtra("description",etDescription.getText().toString().trim());
                if (rbPhysical.isChecked()){
                    intent.putExtra("service_type",rbPhysical.getText().toString());
                }else {
                    intent.putExtra("service_type",rbOnline.getText().toString());
                }
                intent.putExtra("lat","33.684422");
                intent.putExtra("long" , "73.047882");
                startActivity(intent);
                finish();
            }
        });
    }
}
