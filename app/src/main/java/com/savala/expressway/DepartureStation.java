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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.savala.expressway.adapter.AdapterDepartureStation;
import com.savala.expressway.fragment.AccountFragment;
import com.savala.expressway.model.ModelTollStations;

import java.util.ArrayList;
import java.util.List;

public class DepartureStation extends AppCompatActivity {

    private ImageView mBack, mDone;

    private TextView mDeparture, mStation;

    private TextView mPickText;

    private String station_uid;
    private String station_name;

    private DatabaseReference uRef;

    RecyclerView recyclerView;
    AdapterDepartureStation adapterDepartureStation;
    List<ModelTollStations> stationList;

    public DepartureStation(){}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_departure_station);

        Intent intent = getIntent();
        station_uid = intent.getStringExtra("station_uid");

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

        //init recyclerview
        recyclerView = findViewById(R.id.departure_station_recycler_view);
        recyclerView.setNestedScrollingEnabled(false);

        //set its properties
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //init userList
        stationList = new ArrayList<>();

        getStations();

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DepartureStation.this, AccountFragment.class);
                intent.putExtra("station_uid", station_uid);
                startActivity(intent);
                finish();
            }
        });

        //get info of picked station
        uRef = FirebaseDatabase.getInstance().getReference("TollStation");
        Query dbQuery = uRef.orderByChild("station_id").equalTo(station_uid);
        dbQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    station_name = "" + ds.child("display_name").getValue();

                    mPickText.setText(station_name);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getStations(){
        //get path of database
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("TollStation");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                stationList.clear();

                for (DataSnapshot ds : snapshot.getChildren()) {
                    //get data
                    ModelTollStations station = ds.getValue(ModelTollStations.class);

                    stationList.add(station);
                }

                //adapter
                adapterDepartureStation = new AdapterDepartureStation(DepartureStation.this, stationList);

                //refresh adapter
                adapterDepartureStation.notifyDataSetChanged();

                //set to recyclerView
                recyclerView.setAdapter(adapterDepartureStation);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}