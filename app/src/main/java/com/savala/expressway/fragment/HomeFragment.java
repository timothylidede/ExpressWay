package com.savala.expressway.fragment;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
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
import com.savala.expressway.DepartureStation;
import com.savala.expressway.DestinationStation;
import com.savala.expressway.R;

public class HomeFragment extends BaseFragment{

    //widgets
    private TextView mSearchTitle, mWhenTitle;
    private TextView mExpress, mWay;
    private TextView mDepartureTitle, mDestinationTitle;
    private String departure, destination;

    private CardView mDepartureStation, mDestinationStation;

    private String clicked = "false";

    private ImageView mExchange;

    private String on = "on";

    private String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();

    public static HomeFragment create(){
        return new HomeFragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    public void inOnCreateView(View root, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        RelativeLayout layout = (RelativeLayout) root.findViewById(R.id.layout);
        AnimationDrawable animationDrawable = (AnimationDrawable) layout.getBackground();
        animationDrawable.setEnterFadeDuration(3000);
        animationDrawable.setExitFadeDuration(3000);
        animationDrawable.start();

        RelativeLayout layout1 = (RelativeLayout) root.findViewById(R.id.layout1);
        AnimationDrawable animationDrawable1 = (AnimationDrawable) layout1.getBackground();
        animationDrawable1.setEnterFadeDuration(3000);
        animationDrawable1.setExitFadeDuration(3000);
        animationDrawable1.start();

        RelativeLayout top_layout = (RelativeLayout) root.findViewById(R.id.top_layout);
        AnimationDrawable top_layout1 = (AnimationDrawable) top_layout.getBackground();
        top_layout1.setEnterFadeDuration(3000);
        top_layout1.setExitFadeDuration(3000);
        top_layout1.start();

        mSearchTitle = (TextView) root.findViewById(R.id.search_title);
        mWhenTitle = (TextView) root.findViewById(R.id.when_title);
        mExpress = root.findViewById(R.id.express_title);
        mWay = root.findViewById(R.id.way_title);

        mDepartureStation = (CardView) root.findViewById(R.id.from_destination);
        mDestinationStation = (CardView) root.findViewById(R.id.to_destination);

        mDepartureTitle = (TextView) root.findViewById(R.id.from_destination_name);
        mDestinationTitle = (TextView) root.findViewById(R.id.to_destination_name);
        departure = mDepartureTitle.getText().toString().trim();
        destination = mDestinationTitle.getText().toString().trim();

        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Poppins-SemiBold.ttf");
        Typeface tf2 = Typeface.createFromAsset(getContext().getAssets(), "fonts/Poppins-Regular.ttf");
        mSearchTitle.setTypeface(tf);
        mWhenTitle.setTypeface(tf2);
        mExpress.setTypeface(tf);
        mWay.setTypeface(tf);

        setDestinationStation();
        setDepartureStation();

        mExchange = (ImageView) root.findViewById(R.id.exchange);
        mExchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RotateAnimation rotate = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotate.setDuration(500);
                rotate.setInterpolator(new LinearInterpolator());
                mExchange.setAnimation(rotate);

                if(on.equals("on")){
                    on = "off";
                    mDestinationTitle.setText(departure);
                    mDepartureTitle.setText(destination);
                }else{
                    on = "on";
                    mDepartureTitle.setText(departure);
                    mDestinationTitle.setText(destination);
                }
            }
        });

        mDepartureStation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicked = "true";
                Intent intent = new Intent(getContext(), DepartureStation.class);
                startActivity(intent);
            }
        });

        mDestinationStation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), DestinationStation.class);
                startActivity(intent);
            }
        });
    }

    private void setDepartureStation() {

        FirebaseDatabase.getInstance().getReference("ResumeBookings")
                .orderByChild("user_id").equalTo(user_id)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot ds:snapshot.getChildren()){
                            String departure_title = "" + ds.child("departure_station").getValue();

                            mDepartureTitle.setText(departure_title);

                            if(departure_title.equals("")){
                                mDepartureTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP,17);
                                mDepartureTitle.setTextColor(Color.GRAY);
                                mDepartureTitle.setText("Enter Departure Station");
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void setDestinationStation() {

        FirebaseDatabase.getInstance().getReference("ResumeBookings")
                .orderByChild("user_id").equalTo(user_id)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot ds:snapshot.getChildren()){
                            String destination_title = "" + ds.child("destination_station").getValue();

                            mDestinationTitle.setText(destination_title);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}
