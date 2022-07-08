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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Calendar;

public class JourneyDate extends AppCompatActivity {

    private TextView mExpress, mWay, mPickedDate, mPickedMonth, mPickedYear;

    private ImageView mBack, mDone;

    private ProgressBar mProgressBar;

    private LinearLayout mToday, mTomorrow;

    private CalendarView mCalendarView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_journey_date);

        mPickedDate = (TextView) findViewById(R.id.picked_date);
        mPickedMonth = (TextView) findViewById(R.id.picked_month);
        mPickedYear = (TextView) findViewById(R.id.picked_year);

        mDone = (ImageView) findViewById(R.id.done);

        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);

        String pickedDate = mPickedDate.getText().toString().trim();
        String pickedMonth = mPickedMonth.getText().toString().trim();
        String pickedYear = mPickedYear.getText().toString().trim();

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);

        pickedDate = "" + dayOfMonth;
        pickedMonth = "" + getMonthFormat(month);
        pickedYear = "" + year;

        mPickedDate.setText(pickedDate);
        mPickedMonth.setText(pickedMonth);
        mPickedYear.setText(pickedYear);

        mExpress = findViewById(R.id.express_title);
        mWay = findViewById(R.id.way_title);

        mCalendarView = findViewById(R.id.calendar_view);
        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                month = month + 1;

                mPickedDate.setText("" + dayOfMonth);
                mPickedMonth.setText("" + getMonthFormat(month));
                mPickedYear.setText("" + year);
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

        mDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
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