package com.savala.expressway;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;

public class JourneyDate extends AppCompatActivity {

    private TextView mExpress, mWay, mPickedDate, mPickedMonth, mPickedYear,
            mTodayDate, mTodayMonth, mTodayYear,
            mTomorrowDate, mTomorrowMonth, mTomorrowYear, mTDA, mTMW;

    private ImageView mBack, mDone;

    private ProgressBar mProgressBar;

    private LinearLayout mToday, mTomorrow;

    private CalendarView mCalendarView;

    private String user_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_journey_date);

        user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mPickedDate = (TextView) findViewById(R.id.picked_date);
        mPickedMonth = (TextView) findViewById(R.id.picked_month);
        mPickedYear = (TextView) findViewById(R.id.picked_year);

        mTodayDate = (TextView) findViewById(R.id.today_date);
        mTodayMonth = (TextView) findViewById(R.id.today_month);
        mTodayYear = (TextView) findViewById(R.id.today_year);

        mTomorrowDate = (TextView) findViewById(R.id.tomorrow_date);
        mTomorrowMonth = (TextView) findViewById(R.id.tomorrow_month);
        mTomorrowYear = (TextView) findViewById(R.id.tomorrow_year);

        mDone = (ImageView) findViewById(R.id.done);

        mTDA = (TextView) findViewById(R.id.tda);
        mTMW = (TextView) findViewById(R.id.tmw);

        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        int tomorrow = dayOfMonth + 1;

        String pickedDate = "" + dayOfMonth;
        String pickedMonth = "" + getMonthFormat(month + 1);
        String pickedYear = "" + year;

        String todayDate = "" + dayOfMonth;
        String todayMonth = "" + getMonthFormat(month + 1);
        String todayYear = "" + year;
        String tomorrowDate = "" + tomorrow;

        mPickedDate.setText(pickedDate);
        mPickedMonth.setText(pickedMonth);
        mPickedYear.setText(pickedYear);

        mTodayDate.setText(todayDate);
        mTodayMonth.setText(todayMonth);
        mTodayYear.setText(todayYear);

        mTomorrowDate.setText(tomorrowDate);
        mTomorrowMonth.setText(todayMonth);
        mTomorrowYear.setText(todayYear);

        mExpress = findViewById(R.id.express_title);
        mWay = findViewById(R.id.way_title);

        mCalendarView = findViewById(R.id.calendar_view);
        mCalendarView.setMinDate(System.currentTimeMillis()-1000);
        mCalendarView.setMaxDate(System.currentTimeMillis() + 604800000);

        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year,
                                            int month, int dayOfMonth) {
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
                mTDA.setTextColor(Color.parseColor("#243665"));
                mTodayDate.setTextColor(Color.parseColor("#000000"));
                mTodayMonth.setTextColor(Color.parseColor("#000000"));
                mTodayYear.setTextColor(Color.parseColor("#000000"));

                mTMW.setTextColor(Color.parseColor("#989898"));
                mTomorrowDate.setTextColor(Color.parseColor("#989898"));
                mTomorrowMonth.setTextColor(Color.parseColor("#989898"));
                mTomorrowYear.setTextColor(Color.parseColor("#989898"));

                Calendar cal = Calendar.getInstance();
                int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);

                String today = "" + dayOfMonth;
                mPickedDate.setText(today);
            }
        });

        mTomorrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTMW.setTextColor(Color.parseColor("#243665"));
                mTomorrowDate.setTextColor(Color.parseColor("#000000"));
                mTomorrowMonth.setTextColor(Color.parseColor("#000000"));
                mTomorrowYear.setTextColor(Color.parseColor("#000000"));

                mTDA.setTextColor(Color.parseColor("#989898"));
                mTodayDate.setTextColor(Color.parseColor("#989898"));
                mTodayMonth.setTextColor(Color.parseColor("#989898"));
                mTodayYear.setTextColor(Color.parseColor("#989898"));

                Calendar cal = Calendar.getInstance();
                int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
                int tmrw = dayOfMonth + 1;

                String tomorrow = "" + tmrw;
                mPickedDate.setText(tomorrow);
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
                mDone.setVisibility(View.INVISIBLE);
                mProgressBar.setVisibility(View.VISIBLE);

                String day = mPickedDate.getText().toString().trim();
                String month = mPickedMonth.getText().toString().trim();
                String year = mPickedYear.getText().toString().trim();

                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("day", day);
                hashMap.put("month", month);
                hashMap.put("year", year);

                FirebaseDatabase.getInstance().getReference("ResumeBookings")
                        .child(user_id)
                        .updateChildren(hashMap)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    finish();
                                }else{
                                    Toast.makeText(JourneyDate.this,
                                            "Try Again", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
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