package com.savala.expressway;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.savala.expressway.fragment.AccountFragment;

import java.util.ArrayList;
import java.util.List;

public class DepartureStation extends AppCompatActivity {

    private ImageView mBack, mDone;

    private TextView mDeparture, mStation;

    private TextView mPickText;

    private CardView mMlolongo, mSgr, mJkia, mEastern, mSouthern, mCapital, mHaile, mMuseum, mWestlands, mJames;

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

        String station = (String) mPickText.getText();

        if(station.equals("Mlolongo") ||
        station.equals("Standard Gauge Railway")||
        station.equals("Jomo Kenyatta International Airport") ||
        station.equals("Eastern Bypass") ||
        station.equals("Southern Bypass") ||
        station.equals("Capital Centre") ||
        station.equals("Haile Selassie Avenue") ||
        station.equals("Museum Hill") ||
        station.equals("Westlands") ||
        station.equals("James Gichuru Road")){
            mDone.setVisibility(View.VISIBLE);
        }
        else{
            mDone.setVisibility(View.INVISIBLE);
        }

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
            }
        });

        mSgr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPickText.setText("Standard Gauge Railway");
            }
        });

        mJkia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPickText.setText("Jomo Kenyatta International Airport");
            }
        });

        mEastern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPickText.setText("Eastern Bypass");
            }
        });

        mSouthern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPickText.setText("Southern Bypass");
            }
        });

        mCapital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPickText.setText("Capital Centre");
            }
        });

        mHaile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPickText.setText("Haile Selassie");
            }
        });

        mMuseum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPickText.setText("Museum Hill");
            }
        });

        mWestlands.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPickText.setText("Westlands");
            }
        });

        mJames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPickText.setText("James Gichuru Road");
            }
        });

        mDeparture = findViewById(R.id.departure_title);
        mStation = findViewById(R.id.station_title);

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

            }
        });
    }
}