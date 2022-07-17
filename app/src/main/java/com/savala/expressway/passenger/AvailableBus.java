package com.savala.expressway.passenger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.savala.expressway.R;
import com.savala.expressway.adapter.AdapterBus;
import com.savala.expressway.model.ModelBus;

import java.util.ArrayList;

public class AvailableBus extends AppCompatActivity {

    private ImageView mDone, mBack;

    private TextView mBusesFound, mDate, mRoute, mDay, mBuss;

    private ProgressBar mProgressBar;

    private RelativeLayout mNothing, mRecycler;

    private RecyclerView recyclerView;
    private AdapterBus adapterBus;
    ArrayList<ModelBus> busList;

    private int mBusCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_available_bus);

        mDone = (ImageView) findViewById(R.id.done);
        mDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        RelativeLayout top_layout = (RelativeLayout) findViewById(R.id.top_layout);
        AnimationDrawable top_layout1 = (AnimationDrawable) top_layout.getBackground();
        top_layout1.setEnterFadeDuration(3000);
        top_layout1.setExitFadeDuration(3000);
        top_layout1.start();

        mNothing = (RelativeLayout) findViewById(R.id.nothing);
        mRecycler = (RelativeLayout) findViewById(R.id.recycler);

        mBack = (ImageView) findViewById(R.id.back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);

        mBusesFound = (TextView) findViewById(R.id.buses_found);
        mDate = (TextView) findViewById(R.id.date);
        mRoute = (TextView) findViewById(R.id.route);
        mDay = (TextView) findViewById(R.id.day);
        mBuss = (TextView) findViewById(R.id.buss);

        //init recyclerview
        recyclerView = findViewById(R.id.bus_recycler_view);
        recyclerView.setNestedScrollingEnabled(false);

        //set its properties
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //init userList
        busList = new ArrayList<>();

        setDate();
        setRoute();
        getBuses();
        getBusCount();
    }

    private void setRoute() {
        FirebaseDatabase.getInstance().getReference("Bookings")
                .orderByChild("user_id").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot ds:snapshot.getChildren()){
                            String departure_station = "" + ds.child("departure_station").getValue();
                            String destination_station = "" + ds.child("destination_station").getValue();

                            String route = departure_station + " - " + destination_station;

                            mRoute.setText(route);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void setDate() {
        FirebaseDatabase.getInstance().getReference("Bookings")
                .orderByChild("user_id").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot ds:snapshot.getChildren()){
                            String day = "" + ds.child("day").getValue();
                            String month = "" + ds.child("month").getValue();
                            String year = "" + ds.child("year").getValue();
                            String destination_station = "" +ds.child("destination_station").getValue();
                            String departure_station = "" +ds.child("departure_station").getValue();

                            String date = day + " " + month + " " + year;

                            mDate.setText(date);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void getBuses() {
        FirebaseDatabase.getInstance().getReference("Bookings")
                .orderByChild("user_id").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot ds:snapshot.getChildren()){
                            String day = "" + ds.child("day").getValue();

                            FirebaseDatabase.getInstance().getReference("Bus").
                                    orderByChild("day").equalTo(day)
                                    .addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            for (DataSnapshot ds : snapshot.getChildren()) {
                                                String route = "" +ds.child("route").getValue();

                                                FirebaseDatabase.getInstance().getReference("Bus").
                                                        orderByChild("route").equalTo(route)
                                                        .addValueEventListener(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                busList.clear();

                                                                for (DataSnapshot ds : snapshot.getChildren()) {
                                                                    //get data
                                                                    ModelBus bus = ds.getValue(ModelBus.class);

                                                                    busList.add(bus);
                                                                }

                                                                //adapter
                                                                adapterBus = new AdapterBus(AvailableBus.this, busList);
                                                                //set to recyclerView
                                                                recyclerView.setAdapter(adapterBus);
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

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void getBusCount(){
        mBusCount = 0;

        FirebaseDatabase.getInstance().getReference("Bookings")
                .orderByChild("user_id").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot ds:snapshot.getChildren()){
                            String day = "" + ds.child("day").getValue();

                            FirebaseDatabase.getInstance().getReference("Bus")
                                    .orderByChild("day").equalTo(day)
                                    .addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            for(DataSnapshot ds: snapshot.getChildren()){
                                                String route = "" +ds.child("route").getValue();

                                                FirebaseDatabase.getInstance().getReference("Bus")
                                                        .orderByChild("route").equalTo(route)
                                                        .addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                for(DataSnapshot singleSnapshot : snapshot.getChildren()){
                                                                    mBusCount++;
                                                                }
                                                                mBusesFound.setText(String.valueOf(mBusCount));

                                                                int bus = Integer.parseInt(mBusesFound.getText().toString());

                                                                if(bus <= 0){
                                                                    mBusesFound.setText("0");
                                                                    mNothing.setVisibility(View.VISIBLE);
                                                                    mRecycler.setVisibility(View.INVISIBLE);
                                                                    mDone.setVisibility(View.INVISIBLE);
                                                                }else if(bus == 1){
                                                                    mBuss.setText("Bus Found");
                                                                    mNothing.setVisibility(View.GONE);
                                                                    mRecycler.setVisibility(View.VISIBLE);
                                                                } else{
                                                                    mNothing.setVisibility(View.GONE);
                                                                    mRecycler.setVisibility(View.VISIBLE);
                                                                }

                                                                mBusCount = 0;
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

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}