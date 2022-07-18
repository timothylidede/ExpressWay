package com.savala.expressway.passenger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.savala.expressway.R;

import java.util.HashMap;

public class BoardingPoint extends AppCompatActivity implements OnMapReadyCallback {

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (mLocationPermissionsGranted) {
            setMapLocation();

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            }
            mMap.setMyLocationEnabled(true);
        }
    }

    private TextView mExpress, mWay;

    private TextView mLocation, mTime;
    private GoogleMap mMap;

    private CardView mNextButton;

    private ProgressBar mProgressBar;

    private EditText mPhoneNumber;

    //constants
    private static final int ERROR_DIALOG_REQUEST = 9001;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private Boolean mLocationPermissionsGranted = false;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;

    private String number_plate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_boarding_point);

        Intent intent = getIntent();
        number_plate = intent.getStringExtra("number_plate");

        mExpress = findViewById(R.id.express_title);
        mWay = findViewById(R.id.way_title);

        mNextButton = findViewById(R.id.next_button);
        mProgressBar = findViewById(R.id.progressBar);

        mPhoneNumber = findViewById(R.id.phone_number);

        mLocation = findViewById(R.id.location);
        mTime = findViewById(R.id.time);

        RelativeLayout top_layout = (RelativeLayout) findViewById(R.id.top_layout);
        AnimationDrawable top_layout1 = (AnimationDrawable) top_layout.getBackground();
        top_layout1.setEnterFadeDuration(3000);
        top_layout1.setExitFadeDuration(3000);
        top_layout1.start();

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Poppins-SemiBold.ttf");
        Typeface tf2 = Typeface.createFromAsset(getAssets(), "fonts/Poppins-Regular.ttf");

        mExpress.setTypeface(tf);
        mWay.setTypeface(tf);

        mNextButton = findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProgressBar.setVisibility(View.VISIBLE);
                mNextButton.setVisibility(View.INVISIBLE);
                next();
            }
        });

        getLocationPermission();
        setLocationDetails();
        setTimeDetails();
    }

    private void next() {
        String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String phone_number = mPhoneNumber.getText().toString().trim();
        String booking_id = "" + System.currentTimeMillis();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("user_id", user_id);
        hashMap.put("number_plate", number_plate);
        hashMap.put("booking_id", booking_id);

        FirebaseDatabase.getInstance().getReference("Bus")
                .orderByChild("number_plate").equalTo(number_plate)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot ds: snapshot.getChildren()){
                            int seats = Integer.parseInt("" + ds.child("seats").getValue());

                            String number_of_seats = String.valueOf(seats - 1);

                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("seats", number_of_seats);

                            FirebaseDatabase.getInstance().getReference("Bus")
                                    .child(number_plate)
                                    .updateChildren(hashMap);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        FirebaseDatabase.getInstance().getReference("User")
                .orderByChild("userID").equalTo(user_id)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot ds: snapshot.getChildren()){
                            String first_name = "" + ds.child("first_name").getValue();
                            String last_name = "" + ds.child("last_name").getValue();

                            HashMap<String, Object> hashMap2 = new HashMap<>();
                            hashMap2.put("first_name", first_name);
                            hashMap2.put("last_name", last_name);
                            hashMap2.put("phone_number", phone_number);
                            hashMap2.put("user_id", user_id);

                            FirebaseDatabase.getInstance().getReference("Passenger")
                                    .child(booking_id)
                                    .setValue(hashMap2);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        FirebaseDatabase.getInstance().getReference("DoneBooking")
                .child(booking_id)
                .setValue(hashMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(BoardingPoint.this, "Successfully booked", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void setTimeDetails() {
        FirebaseDatabase.getInstance().getReference("Driver")
                .orderByChild("user_id").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot ds: snapshot.getChildren()){
                            String number_plate = "" + ds.child("number_plate").getValue();

                            FirebaseDatabase.getInstance().getReference("Bus")
                                    .orderByChild("number_plate").equalTo(number_plate)
                                    .addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            for(DataSnapshot ds: snapshot.getChildren()){
                                                String timezone = "" + ds.child("timezone").getValue();

                                                String mHour = "" + ds.child("hour").getValue();
                                                int hour = Integer.parseInt(mHour);

                                                String real_hour = String.valueOf(hour - 1);

                                                mTime.setText(real_hour + ":50 " + timezone);
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void setLocationDetails() {
        FirebaseDatabase.getInstance().getReference("Driver")
                .orderByChild("user_id").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot ds: snapshot.getChildren()){
                            String number_plate = "" + ds.child("number_plate").getValue();

                            FirebaseDatabase.getInstance().getReference("Bus")
                                    .orderByChild("number_plate").equalTo(number_plate)
                                    .addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            for(DataSnapshot ds: snapshot.getChildren()){
                                                String location = "" + ds.child("place_name").getValue();

                                                mLocation.setText(location);
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }

    private void getLocationPermission() {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionsGranted = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    public boolean isServicesOK() {
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(BoardingPoint.this);

        if (available == ConnectionResult.SUCCESS) {
            //everything is fine and the user can make map request


        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            //an error occurred but we can resolve it
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(BoardingPoint.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            Toast.makeText(this, "Unable to make map requests", Toast.LENGTH_SHORT).show();
        }

        return false;
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(BoardingPoint.this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mLocationPermissionsGranted = false;

        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionsGranted = false;
                            return;
                        }
                    }
                    mLocationPermissionsGranted = true;
                    //initialize map
                    initMap();
                }
            }
        }

    }

    private void setMapLocation() {

        FirebaseDatabase.getInstance().getReference("Driver")
                .orderByChild("user_id").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot ds: snapshot.getChildren()){
                            String number_plate = "" + ds.child("number_plate").getValue();

                            FirebaseDatabase.getInstance().getReference("Bus")
                                    .orderByChild("number_plate").equalTo(number_plate)
                                    .addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            for(DataSnapshot ds: snapshot.getChildren()){
                                                String place_name = "" + ds.child("place_name").getValue();
                                                float latitude = Float.parseFloat("" + ds.child("latitude").getValue());
                                                float longitude = Float.parseFloat("" + ds.child("longitude").getValue());

                                                LatLng latLng = new LatLng(latitude, longitude);

                                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13f));
                                                MarkerOptions options = new MarkerOptions()
                                                        .position(latLng)
                                                        .title(place_name);
                                                mMap.addMarker(options);

                                                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}