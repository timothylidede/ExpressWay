package com.savala.expressway.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.savala.expressway.passenger.DepartureStation;
import com.savala.expressway.passenger.DestinationStation;
import com.savala.expressway.passenger.JourneyDate;
import com.savala.expressway.R;
import com.savala.expressway.passenger.AvailableBus;

import java.util.Calendar;
import java.util.HashMap;

public class HomeFragment extends BaseFragment{

    //widgets
    private TextView mSearchTitle, mDate, mMonth, mYear;
    private TextView mExpress, mWay;
    private TextView mDepartureTitle, mDestinationTitle;
    private String departure, destination;

    private ProgressBar mProgressbar1, mProgressbar2, mProgressBar3, mProgressBar4;

    private CardView mDepartureStation, mDestinationStation, mJourneyDetails, mSearchButton;

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

        mProgressbar1 = (ProgressBar) root.findViewById(R.id.progress_bar1);
        mProgressbar2 = (ProgressBar) root.findViewById(R.id.progress_bar2);
        mProgressBar3 = (ProgressBar) root.findViewById(R.id.progress_bar3);
        mProgressBar4 = (ProgressBar) root.findViewById(R.id.progress_bar4);

        mSearchTitle = (TextView) root.findViewById(R.id.search_title);

        mDate = (TextView) root.findViewById(R.id.date);
        mMonth = (TextView) root.findViewById(R.id.month);
        mYear = (TextView) root.findViewById(R.id.year);

        mExpress = root.findViewById(R.id.express_title);
        mWay = root.findViewById(R.id.way_title);

        mDepartureStation = (CardView) root.findViewById(R.id.from_destination);
        mDestinationStation = (CardView) root.findViewById(R.id.to_destination);
        mJourneyDetails = (CardView) root.findViewById(R.id.journey_details);
        
        mSearchButton = (CardView) root.findViewById(R.id.search_button); 

        mDepartureTitle = (TextView) root.findViewById(R.id.from_destination_name);
        mDestinationTitle = (TextView) root.findViewById(R.id.to_destination_name);
        departure = mDepartureTitle.getText().toString().trim();
        destination = mDestinationTitle.getText().toString().trim();

        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Poppins-SemiBold.ttf");
        Typeface tf2 = Typeface.createFromAsset(getContext().getAssets(), "fonts/Poppins-Regular.ttf");
        mSearchTitle.setTypeface(tf);
        mExpress.setTypeface(tf);
        mWay.setTypeface(tf);

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);

        String pickedDate = "" + dayOfMonth;
        String pickedMonth = "" + getMonthFormat(month + 1);
        String pickedYear = "" + year;

        mDate.setText(pickedDate);
        mMonth.setText(pickedMonth);
        mYear.setText(pickedYear);

        mJourneyDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), JourneyDate.class);
                startActivity(intent);
            }
        });
        
        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProgressBar4.setVisibility(View.VISIBLE);
                mSearchButton.setVisibility(View.INVISIBLE);

                String departure = mDepartureTitle.getText().toString().trim();
                String destination = mDestinationTitle.getText().toString().trim();
                String day = mDate.getText().toString().trim();
                String month = mMonth.getText().toString().trim();
                String year = mYear.getText().toString().trim();
                String timestamp = "" + System.currentTimeMillis();
                String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();

                if(departure.equals(destination) || destination.equals(departure)){
                    Toast.makeText(getContext(), "Departure and destination stations cannot be similar", Toast.LENGTH_LONG).show();
                    mProgressBar4.setVisibility(View.INVISIBLE);
                    mSearchButton.setVisibility(View.VISIBLE);
                }else if(departure.equals("Mlolongo") ||
                        departure.equals("Standard Gauge Railway") ||
                        departure.equals("Jomo Kenyatta International Airport") ||
                        departure.equals("Eastern Bypass") ||
                        departure.equals("Southern Bypass") ||
                        departure.equals("Capital Centre") ||
                        departure.equals("Haile Selassie Avenue") ||
                        departure.equals("Museum Hill") ||
                        departure.equals("Westlands") ||
                        departure.equals("James Gichuru Road")){
                    
                    if(destination.equals("Mlolongo") ||
                            destination.equals("Standard Gauge Railway") ||
                            destination.equals("Jomo Kenyatta International Airport") ||
                            destination.equals("Eastern Bypass") ||
                            destination.equals("Southern Bypass") ||
                            destination.equals("Capital Centre") ||
                            destination.equals("Haile Selassie Avenue") ||
                            destination.equals("Museum Hill") ||
                            destination.equals("Westlands") ||
                            destination.equals("James Gichuru Road")){

                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("destination_station", destination);
                        hashMap.put("departure_station", departure);
                        hashMap.put("day", day);
                        hashMap.put("timestamp", timestamp);
                        hashMap.put("user_id", user_id);
                        hashMap.put("month", month);
                        hashMap.put("year", year);

                        FirebaseDatabase.getInstance().getReference("Bookings")
                                .child(timestamp)
                                .setValue(hashMap)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            mProgressBar4.setVisibility(View.INVISIBLE);
                                            mSearchButton.setVisibility(View.VISIBLE);

                                            Intent intent = new Intent(getContext(), AvailableBus.class);
                                            startActivity(intent);
                                        }
                                    }
                                });
                        
                    }else{
                        Toast.makeText(getContext(), "Pick valid destination station", Toast.LENGTH_SHORT).show();
                    }
                    
                }else{
                    Toast.makeText(getContext(), "Pick valid departure station", Toast.LENGTH_SHORT).show();
                }
            }
        });

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

        setDestinationStation();
        setDepartureStation();
        setDateDetails();
    }

    private void setDepartureStation() {
        mProgressbar1.setVisibility(View.VISIBLE);

        FirebaseDatabase.getInstance().getReference("ResumeBookings")
                .orderByChild("user_id").equalTo(user_id)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot ds:snapshot.getChildren()){
                            String departure_title = "" + ds.child("departure_station").getValue();

                            mDepartureTitle.setText(departure_title);
                            departure = departure_title;
                            mProgressbar1.setVisibility(View.INVISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                            mDepartureTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP,17);
                            mDepartureTitle.setTextColor(Color.GRAY);
                            mDepartureTitle.setText("Enter Departure Station");
                    }
                });
    }

    private void setDestinationStation() {
        mProgressbar2.setVisibility(View.VISIBLE);

        FirebaseDatabase.getInstance().getReference("ResumeBookings")
                .orderByChild("user_id").equalTo(user_id)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot ds:snapshot.getChildren()){
                            String destination_title = "" + ds.child("destination_station").getValue();

                            mDestinationTitle.setText(destination_title);
                            destination = destination_title;

                            mProgressbar2.setVisibility(View.INVISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void setDateDetails(){
        mProgressBar3.setVisibility(View.INVISIBLE);

        FirebaseDatabase.getInstance().getReference("ResumeBookings")
                .orderByChild("user_id").equalTo(user_id)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot ds:snapshot.getChildren()){
                            String day = "" + ds.child("day").getValue();
                            String month = "" + ds.child("month").getValue();
                            String year = "" + ds.child("year").getValue();

                            mDate.setText(day);
                            mMonth.setText(month);
                            mYear.setText(year);

                            mProgressBar3.setVisibility(View.INVISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private String getMonthFormat(int month){
        if(month == 1)
            return "Jan";
        if(month == 2)
            return "Feb";
        if(month == 3)
            return "Mar";
        if(month == 4)
            return "Apr";
        if(month == 5)
            return "May";
        if(month == 6)
            return "Jun";
        if(month == 7)
            return "Jul";
        if(month == 8)
            return "Aug";
        if(month == 9)
            return "Sep";
        if(month == 10)
            return "Oct";
        if(month == 11)
            return "Nov";
        if(month == 12)
            return "Dec";

        return "Jul";
    }
}
