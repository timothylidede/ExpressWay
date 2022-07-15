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
import com.savala.expressway.DriverInformation;
import com.savala.expressway.R;

public class BookingsFragment extends BaseFragment{
    private CardView mContinueButton, mCreateTripCard, mBusDetailsCard, mTripHistory;
    private TextView mExpress, mWay, mBuss, mText;
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

        mCreateTripCard = (CardView) root.findViewById(R.id.create_trip_card);
        mCreateTripCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        RelativeLayout top_layout = (RelativeLayout) root.findViewById(R.id.top_layout);
        AnimationDrawable top_layout1 = (AnimationDrawable) top_layout.getBackground();
        top_layout1.setEnterFadeDuration(3000);
        top_layout1.setExitFadeDuration(3000);
        top_layout1.start();


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
