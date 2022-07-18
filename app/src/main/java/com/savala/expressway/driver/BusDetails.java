package com.savala.expressway.driver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.savala.expressway.R;

public class BusDetails extends AppCompatActivity {

    private ImageView mBack;
    private TextView mExpress, mWay;

    private TextView mManufacturer, mNumberPlate, mYear, mNumberSeats, mRating;

    private CardView mDoneButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_details);

        mManufacturer = findViewById(R.id.manufacturer);
        mNumberPlate = findViewById(R.id.number_plate);
        mYear = findViewById(R.id.year_of_make);
        mNumberSeats = findViewById(R.id.number_of_seats);
        mRating = findViewById(R.id.rating);

        mDoneButton = findViewById(R.id.done_button);
        mDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mBack = findViewById(R.id.back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mExpress = findViewById(R.id.express_title);
        mWay = findViewById(R.id.way_title);
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Poppins-SemiBold.ttf");
        Typeface tf2 = Typeface.createFromAsset(getAssets(), "fonts/Poppins-Regular.ttf");
        mExpress.setTypeface(tf);
        mWay.setTypeface(tf);

        setBusDetails();
    }

    private void setBusDetails() {
        FirebaseDatabase.getInstance().getReference("Driver")
                .orderByChild("user_id").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot ds:snapshot.getChildren()){
                            String number_plate = "" + ds.child("number_plate").getValue();
                            String bus_manufacturer = "" + ds.child("bus_manufacturer").getValue();
                            String year = "" + ds.child("year").getValue();
                            String number_of_seats = "" + ds.child("number_of_seats").getValue();
                            String rating = "" + ds.child("rating").getValue();

                            mManufacturer.setText(bus_manufacturer);
                            mNumberPlate.setText(number_plate);
                            mYear.setText(year);
                            mNumberSeats.setText(number_of_seats);
                            mRating.setText(rating);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}