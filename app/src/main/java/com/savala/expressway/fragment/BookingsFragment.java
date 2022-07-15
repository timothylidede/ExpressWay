package com.savala.expressway.fragment;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.savala.expressway.driver.CreateTrip;
import com.savala.expressway.driver.DriverInformation;
import com.savala.expressway.R;

public class BookingsFragment extends BaseFragment{
    private CardView mContinueButton, mCreateTripCard, mBusDetailsCard, mTripHistory, mOther;
    private TextView mExpress, mWay, mBuss, mText, mWelcome;
    private ImageView mImage;
    public static BookingsFragment create(){
        return new BookingsFragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_bookings;
    }

    @Override
    public void inOnCreateView(View root, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mExpress = root.findViewById(R.id.express_title);
        mWay = root.findViewById(R.id.way_title);
        mBuss = root.findViewById(R.id.buss);
        mText = root.findViewById(R.id.text);
        mImage = root.findViewById(R.id.image);
        mContinueButton = (CardView) root.findViewById(R.id.continue_button);
        mContinueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), DriverInformation.class);
                startActivity(intent);
            }
        });

        mWelcome = (TextView) root.findViewById(R.id.welcome);

        mCreateTripCard = (CardView) root.findViewById(R.id.create_trip_card);
        mBusDetailsCard = (CardView) root.findViewById(R.id.my_bus_card);
        mTripHistory = (CardView) root.findViewById(R.id.trip_history);
        mOther = (CardView) root.findViewById(R.id.other);

        mCreateTripCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), CreateTrip.class);
                startActivity(intent);
            }
        });

        FirebaseDatabase.getInstance().getReference("Driver")
                .orderByChild("user_id").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot ds: snapshot.getChildren()){
                            String firstname = "" + ds.child("first_name").getValue();

                            mWelcome.setText("Welcome " + firstname);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        FirebaseDatabase.getInstance().getReference("Role")
                .orderByChild("user_id").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot ds:snapshot.getChildren()){
                            String role = "" + ds.child("role").getValue();

                            if(role.equals("user")){
                                mBuss.setVisibility(View.VISIBLE);
                                mText.setVisibility(View.VISIBLE);
                                mContinueButton.setVisibility(View.VISIBLE);
                                mImage.setVisibility(View.VISIBLE);

                                mWelcome.setVisibility(View.INVISIBLE);
                                mCreateTripCard.setVisibility(View.INVISIBLE);
                                mTripHistory.setVisibility(View.INVISIBLE);
                                mBusDetailsCard.setVisibility(View.INVISIBLE);
                                mOther.setVisibility(View.INVISIBLE);
                            }
                            if(role.equals("driver")){
                                mBuss.setVisibility(View.INVISIBLE);
                                mText.setVisibility(View.INVISIBLE);
                                mContinueButton.setVisibility(View.INVISIBLE);
                                mImage.setVisibility(View.INVISIBLE);

                                mWelcome.setVisibility(View.VISIBLE);
                                mCreateTripCard.setVisibility(View.VISIBLE);
                                mTripHistory.setVisibility(View.VISIBLE);
                                mBusDetailsCard.setVisibility(View.VISIBLE);
                                mOther.setVisibility(View.VISIBLE);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Poppins-SemiBold.ttf");
        Typeface tf2 = Typeface.createFromAsset(getContext().getAssets(), "fonts/Poppins-Regular.ttf");

        mExpress.setTypeface(tf);
        mWay.setTypeface(tf);
    }
}
