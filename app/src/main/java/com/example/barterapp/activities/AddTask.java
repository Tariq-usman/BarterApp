package com.example.barterapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.barterapp.R;
import com.example.barterapp.others.Preferences;

import java.util.List;

public class AddTask extends AppCompatActivity {

    private Preferences preferences;
    private Button nextBtn;
    private ImageView backBtn;
    private EditText etTitle, etDescription;
    private TextView tvTaskLocation;
    private RadioButton rbPhysical, rbOnline;
    String latitude,longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        preferences = new Preferences(this);
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
        tvTaskLocation = findViewById(R.id.task_location);
        tvTaskLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences.setLocationStatus("job location");
                startActivity(new Intent(AddTask.this,MapsActivity.class));
            }
        });

        nextBtn = findViewById(R.id.next_btn_add_service);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFromLocation();
                Intent intent = new Intent(AddTask.this,TaskDueDate.class);
                intent.putExtra("title",etTitle.getText().toString().trim());
                intent.putExtra("description",etDescription.getText().toString().trim());
                if (rbPhysical.isChecked()){
                    intent.putExtra("service_type",rbPhysical.getText().toString());
                }else {
                    intent.putExtra("service_type",rbOnline.getText().toString());
                }
                intent.putExtra("lat",latitude);
                intent.putExtra("long" , longitude);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        tvTaskLocation.setText(preferences.getTaskLocation());
    }

    private void getFromLocation()
    {
        Geocoder geocoder = new Geocoder(getBaseContext());
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocationName(preferences.getTaskLocation(), 20);
            for(int i = 0; i < addresses.size(); i++) { // MULTIPLE MATCHES
                Address addr = addresses.get(i);
                latitude = String.valueOf(addr.getLatitude());
                longitude = String.valueOf(addr.getLongitude()); // DO SOMETHING WITH VALUES
            }
        }catch (Exception e){

        }
    }

}
