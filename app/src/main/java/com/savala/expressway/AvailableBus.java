package com.savala.expressway;

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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.savala.expressway.R;
import com.savala.expressway.adapter.AdapterBus;
import com.savala.expressway.model.ModelBus;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AvailableBus extends AppCompatActivity {

    private ImageView mDone, mBack;

    private TextView mBusesFound, mDate;

    private ProgressBar mProgressBar;

    private RecyclerView recyclerView;
    private AdapterBus adapterBus;
    ArrayList<ModelBus> busList;

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
        setDate();

        //init recyclerview
        recyclerView = findViewById(R.id.bus_recycler_view);
        recyclerView.setNestedScrollingEnabled(false);

        //set its properties
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //init userList
        busList = new ArrayList<>();

        getBuses();

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.layout);
        AnimationDrawable animationDrawable = (AnimationDrawable) layout.getBackground();
        animationDrawable.setEnterFadeDuration(3000);
        animationDrawable.setExitFadeDuration(3000);
        animationDrawable.start();
    }

    private void setDate() {
        FirebaseDatabase.getInstance().getReference("ResumeBookings")
                .orderByChild("user_id").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot ds:snapshot.getChildren()){
                            String date = "" + ds.child("date").getValue();

                            mDate.setText(date);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void getBuses() {
        String date = mDate.getText().toString().trim();

        FirebaseDatabase.getInstance().getReference("Bus").
                orderByChild("date").equalTo(date)
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