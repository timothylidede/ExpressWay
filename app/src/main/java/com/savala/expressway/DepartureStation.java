package com.savala.expressway;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.savala.expressway.fragment.HomeFragment;
import com.savala.expressway.model.ModelResumeBookings;

import java.util.HashMap;

public class DepartureStation extends AppCompatActivity {

    private ImageView mBack, mDone;

    private TextView mDeparture, mStation;

    private TextView mPickText;

    private ProgressBar mProgressBar;

    private CardView mMlolongo, mSgr, mJkia, mEastern, mSouthern,
            mCapital, mHaile, mMuseum, mWestlands, mJames;

    private String station = "";

    public DepartureStation(){}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_departure_station);

        mPickText = findViewById(R.id.pick_text);
        mDone = findViewById(R.id.done);
        mDone.setVisibility(View.INVISIBLE);

        mProgressBar = findViewById(R.id.progress_bar);

        mBack = findViewById(R.id.back);

        mMlolongo = findViewById(R.id.mlolongo);
        mSgr = findViewById(R.id.sgr);
        mJkia = findViewById(R.id.jkia);
        mEastern = findViewById(R.id.eastern_bypass);
        mSouthern = findViewById(R.id.southern_bypass);
        mCapital = findViewById(R.id.capital_centre);
        mHaile = findViewById(R.id.haile_selassie);
        mMuseum = findViewById(R.id.museum_hill);
        mWestlands = findViewById(R.id.westlands);
        mJames = findViewById(R.id.james_gichuru);

        mMlolongo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPickText.setText("Mlolongo");
                station = "Mlolongo";
                mDone.setVisibility(View.VISIBLE);
            }
        });

        mSgr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPickText.setText("Standard Gauge Railway");
                station = "Standard Gauge Railway";
                mDone.setVisibility(View.VISIBLE);
            }
        });

        mJkia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPickText.setText("Jomo Kenyatta International Airport");
                station = "Jomo Kenyatta International Airport";
                mDone.setVisibility(View.VISIBLE);
            }
        });

        mEastern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPickText.setText("Eastern Bypass");
                station = "Eastern Bypass";
                mDone.setVisibility(View.VISIBLE);
            }
        });

        mSouthern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPickText.setText("Southern Bypass");
                station = "Southern Bypass";
                mDone.setVisibility(View.VISIBLE);
            }
        });

        mCapital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPickText.setText("Capital Centre");
                station = "Capital Centre";
                mDone.setVisibility(View.VISIBLE);
            }
        });

        mHaile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPickText.setText("Haile Selassie Avenue");
                station = "Haile Selassie Avenue";
                mDone.setVisibility(View.VISIBLE);
            }
        });

        mMuseum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPickText.setText("Museum Hill");
                station = "Museum Hill";
                mDone.setVisibility(View.VISIBLE);
            }
        });

        mWestlands.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPickText.setText("Westlands");
                station = "Westlands";
                mDone.setVisibility(View.VISIBLE);
            }
        });

        mJames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPickText.setText("James Gichuru Road");
                station = "James Gichuru Road";
                mDone.setVisibility(View.VISIBLE);
            }
        });

        mDeparture = findViewById(R.id.departure_title);
        mStation = findViewById(R.id.station_title);

        RelativeLayout top_layout = (RelativeLayout) findViewById(R.id.top_layout);
        AnimationDrawable top_layout1 = (AnimationDrawable) top_layout.getBackground();
        top_layout1.setEnterFadeDuration(3000);
        top_layout1.setExitFadeDuration(3000);
        top_layout1.start();

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Poppins-SemiBold.ttf");
        Typeface tf2 = Typeface.createFromAsset(getAssets(), "fonts/Poppins-Regular.ttf");

        mDeparture.setTypeface(tf);
        mStation.setTypeface(tf);

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.layout);
        AnimationDrawable animationDrawable = (AnimationDrawable) layout.getBackground();
        animationDrawable.setEnterFadeDuration(3000);
        animationDrawable.setExitFadeDuration(3000);
        animationDrawable.start();

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDone.setVisibility(View.INVISIBLE);
                mProgressBar.setVisibility(View.VISIBLE);

                String departure_station = mPickText.getText().toString().trim();
                String timestamp = "" + System.currentTimeMillis();
                String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();

                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("booking_id", timestamp);
                hashMap.put("departure_station", departure_station);
                hashMap.put("user_id", user_id);

                FirebaseDatabase.getInstance().getReference("ResumeBookings")
                        .child(user_id)
                        .child(timestamp)
                        .setValue(hashMap)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Intent i = new Intent();
                            i.putExtra("booking_id", timestamp);
                            setResult(1, i);
                            finish();
                        }else{
                            Toast.makeText(DepartureStation.this, "Try Again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

    }
}