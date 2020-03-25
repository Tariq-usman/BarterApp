package com.example.barterapp.activities;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.barterapp.R;
import com.example.barterapp.others.Preferences;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final int MY_REQUEST_INT = 101;
    private GoogleMap mMap;
    private EditText searchEt;
    private TextView searched_location;
    private ImageView backBtn, locationPinUp, searchBtn, currentLocationBtn;
    private Button doneBtn;
    private LocationManager manager;
    private LatLng current;
    double currentLat;
    double currentLng;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    String centerPosition, task_location_status;
    List<Address> addresses;
    private Preferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MapsActivity.this);

        fetchLastLocation();

        preferences = new Preferences(this);
        task_location_status = preferences.getLocationStatus();
        currentLocationBtn = findViewById(R.id.ivCurrent_location);
        doneBtn = findViewById(R.id.done_btn);
        searchBtn = findViewById(R.id.searchBtn);
        searchEt = findViewById(R.id.searchEt);
        searched_location = findViewById(R.id.searched_location);
        locationPinUp = findViewById(R.id.imgLocationPinUp);
        backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.clear();
                String location = searchEt.getText().toString();
                List<Address> addressList = null;

                if (searchEt.equals("")) {
                    Toast.makeText(getApplicationContext(), "Enter Location:", Toast.LENGTH_LONG).show();
                } else if (location != null || !location.equals("")) {
                    Geocoder geocoder = new Geocoder(getApplicationContext());
                    try {
                        addressList = geocoder.getFromLocationName(location, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Address address = addressList.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
//                    Toast.makeText(getApplicationContext(),""+latLng,Toast.LENGTH_LONG).show();

                    mMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                }
            }
        });

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String passingLocation = searched_location.getText().toString().trim();
                if (task_location_status.equalsIgnoreCase("job location")) {
                    preferences.setTaskLocation(passingLocation);
                } else {
                    preferences.setUserLocation(passingLocation);
                }
                finish();
            }
        });
        currentLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //.icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(R.drawable.ic_locations_green))));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(current));
                    //mMap.animateCamera(CameraUpdateFactory.newLatLng(current));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(current, 15));
                } catch (Exception e) {
                    Log.e("exception ", e.toString());
                }

            }
        });


    }

    LocationManager locationManager;
    LocationListener locationListener;

    private void fetchLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]
                    {android.Manifest.permission.ACCESS_FINE_LOCATION}, MY_REQUEST_INT);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;
                    currentLat = location.getLatitude();
                    currentLng = location.getLongitude();
                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                            .findFragmentById(R.id.map);
                    View mapView = mapFragment.getView();
                    mapFragment.getMapAsync(MapsActivity.this);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                }
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                try {
                    current = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.addMarker(new MarkerOptions()
                            .position(current)
                            .title("Current Location"));
                    //.icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(R.drawable.ic_locations_green))));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(current));
                    //mMap.animateCamera(CameraUpdateFactory.newLatLng(current));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(current, 15));
//                mMap.setMyLocationEnabled(true);

                    if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(MapsActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                    }
                } catch (Exception e) {
                    Log.e("exception", e.toString());
                }
            }

        });
        mMap = googleMap;

        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                LatLng center = mMap.getCameraPosition().target;
                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.ENGLISH);
                List<Address> addresses;

                try {
                    addresses = geocoder.getFromLocation(center.latitude, center.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                    if (addresses.isEmpty()) {
                        addresses = geocoder.getFromLocation(33.684422, 73.047882, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                        String city = addresses.get(0).getLocality();
                        String state = addresses.get(0).getAdminArea();
                        String country = addresses.get(0).getCountryName();
                        String postalCode = addresses.get(0).getPostalCode();
                        String knownName = addresses.get(0).getFeatureName();
                        searched_location.setText(address);
                        locationPinUp.setVisibility(View.INVISIBLE);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                currentLocationBtn.setVisibility(View.INVISIBLE);
                            }
                        }, 2000);
                    } else {
                        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                        String city = addresses.get(0).getLocality();
                        String state = addresses.get(0).getAdminArea();
                        String country = addresses.get(0).getCountryName();
                        String postalCode = addresses.get(0).getPostalCode();
                        String knownName = addresses.get(0).getFeatureName();
                        searched_location.setText(address);
                        locationPinUp.setVisibility(View.INVISIBLE);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                currentLocationBtn.setVisibility(View.INVISIBLE);
                            }
                        }, 2000);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                   /* String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    String city = addresses.get(0).getLocality();
                    String state = addresses.get(0).getAdminArea();
                    String country = addresses.get(0).getCountryName();
                    String postalCode = addresses.get(0).getPostalCode();
                    String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL
                    searchEt.setText(address);*/
            }
        });
        mMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {
                locationPinUp.setVisibility(View.VISIBLE);
                currentLocationBtn.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
