package com.savala.expressway;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class JourneyDate extends AppCompatActivity {

    private TextView mExpress, mWay;

    private ImageView mBack;

    private LinearLayout mToday, mTomorrow;

    private CalendarView mCalendarView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_journey_date);

        mExpress = findViewById(R.id.express_title);
        mWay = findViewById(R.id.way_title);

        mCalendarView = findViewById(R.id.calendar_view);
        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {

            }
        });

        mBack = (ImageView) findViewById(R.id.back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mToday = (LinearLayout) findViewById(R.id.today);
        mTomorrow = (LinearLayout) findViewById(R.id.tomorrow);

        mToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        mTomorrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.layout);
        AnimationDrawable animationDrawable = (AnimationDrawable) layout.getBackground();
        animationDrawable.setEnterFadeDuration(3000);
        animationDrawable.setExitFadeDuration(3000);
        animationDrawable.start();

        RelativeLayout top_layout = (RelativeLayout) findViewById(R.id.top_layout);
        AnimationDrawable top_layout1 = (AnimationDrawable) top_layout.getBackground();
        top_layout1.setEnterFadeDuration(3000);
        top_layout1.setExitFadeDuration(3000);
        top_layout1.start();

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Poppins-SemiBold.ttf");
        Typeface tf2 = Typeface.createFromAsset(getAssets(), "fonts/Poppins-Regular.ttf");

        mExpress.setTypeface(tf);
        mWay.setTypeface(tf);
    }
}