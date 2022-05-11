package com.savala.expressway;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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

    public DepartureStation(){}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_departure_station);

        mBack = findViewById(R.id.back);
        mDone = findViewById(R.id.done);

        mPickText = findViewById(R.id.pick_text);

        if(mPickText.getText().equals("Pick a Station")){
            mDone.setVisibility(View.INVISIBLE);
        }else{
            mPickText.getBackground().setAlpha(255);
            mDone.setVisibility(View.VISIBLE);
        }

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