package com.savala.expressway.driver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.savala.expressway.R;

public class Price extends AppCompatActivity {

    private ImageView mBack;

    private TextView mExpress, mWay;

    private TextView mRoute, mPrice;

    private CardView mNextButton;

    private ProgressBar mProgressBar;

    private String departure_station, destination_station, route, price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_price);

        Intent intent = getIntent();
        departure_station = intent.getStringExtra("departure_station");
        destination_station = intent.getStringExtra("destination_station");

        mRoute = (TextView) findViewById(R.id.route);
        mPrice = (TextView) findViewById(R.id.price);

        mBack = (ImageView) findViewById(R.id.back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mProgressBar = findViewById(R.id.progressBar);
        mNextButton = findViewById(R.id.next_button);

        mExpress = findViewById(R.id.express_title);
        mWay = findViewById(R.id.way_title);
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Poppins-SemiBold.ttf");
        Typeface tf2 = Typeface.createFromAsset(getAssets(), "fonts/Poppins-Regular.ttf");

        mExpress.setTypeface(tf);
        mWay.setTypeface(tf);

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProgressBar.setVisibility(View.VISIBLE);
                mNextButton.setVisibility(View.INVISIBLE);
                next();
            }
        });

        setRoutePrice();
    }

    private void setRoutePrice(){
        route = departure_station + " - " + destination_station;
        mRoute.setText(route);

        FirebaseDatabase.getInstance().getReference("Price")
                .orderByChild("route").equalTo(route)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot ds: snapshot.getChildren()){
                            price = "" + ds.child("price").getValue();
                            mPrice.setText(price);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void next() {
        Intent intent = new Intent(Price.this, .class);
        intent.putExtra("departure_station", departure_station);
        intent.putExtra("destination_station", destination_station);
        intent.putExtra("route", route);
        intent.putExtra("price", price);
        startActivity(intent);
    }
}