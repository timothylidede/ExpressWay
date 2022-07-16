package com.savala.expressway.driver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.savala.expressway.R;

import java.util.Calendar;

public class DateTime extends AppCompatActivity {

    private ImageView mBack;

    private TextView mDate, mTime;

    private CalendarView mCalendarView;

    private TextView mExpress, mWay;

    private AutoCompleteTextView autoCompleteTime;

    private String[] time = {"0000 hrs", "0100 hrs", "0200 hrs", "0300 hrs", "0400 hrs", "0500 hrs",
            "0600 hrs", "0700 hrs", "0800 hrs", "0900 hrs", "1000 hrs", "1100 hrs", "1200 hrs",
            "1300 hrs", "1400 hrs", "1500 hrs", "1600 hrs", "1700 hrs", "1800 hrs", "1900 hrs",
            "2000 hrs", "2100 hrs", "2200 hrs", "2300 hrs"};

    ArrayAdapter<String> adapterTime;

    private CardView mNextButton;

    private ProgressBar mProgressBar;

    private String departure_station, destination_station, route, price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_date_time);

        Intent intent = getIntent();
        departure_station = intent.getStringExtra("departure_station");
        destination_station = intent.getStringExtra("destination_station");
        route = intent.getStringExtra("route");
        price = intent.getStringExtra("price");

        mDate = findViewById(R.id.date);
        mTime = findViewById(R.id.time_text);
        mCalendarView = findViewById(R.id.calendar_view);

        mNextButton = findViewById(R.id.next_button);
        mProgressBar = findViewById(R.id.progressBar);

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);

        String todayDate = "" + dayOfMonth;
        String todayMonth = "" + getMonthFormat(month + 1);
        String todayYear = "" + year;

        String date = todayDate + " " + todayMonth + " " + todayYear;
        mDate.setText(date);

        mCalendarView.setMinDate(System.currentTimeMillis()-1000);
        mCalendarView.setMaxDate(System.currentTimeMillis() + 604800000);

        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year,
                                            int month, int dayOfMonth) {
                month = month + 1;
                String mDay = "" + dayOfMonth;
                String mMonth = "" + month;
                String mYear = "" + year;

                mDate.setText(mDay + " " + mMonth + " " + mYear);
            }
        });

        autoCompleteTime = findViewById(R.id.autoComplete_time);
        adapterTime = new ArrayAdapter<String>(this, R.layout.list, time);
        autoCompleteTime.setAdapter(adapterTime);
        autoCompleteTime.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                String time = parent.getItemAtPosition(position).toString();
                mTime.setText(time);
            }
        });

        mBack = findViewById(R.id.back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProgressBar.setVisibility(View.VISIBLE);
                mNextButton.setVisibility(View.INVISIBLE);
                next();
            }
        });

        mExpress = findViewById(R.id.express_title);
        mWay = findViewById(R.id.way_title);
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Poppins-SemiBold.ttf");
        Typeface tf2 = Typeface.createFromAsset(getAssets(), "fonts/Poppins-Regular.ttf");

        mExpress.setTypeface(tf);
        mWay.setTypeface(tf);
    }

    private void next() {
        String date = mDate.getText().toString().trim();
        String time = mTime.getText().toString().trim();

        if(date.isEmpty()){
            Toast.makeText(DateTime.this, "Select Date", Toast.LENGTH_SHORT).show();
            mProgressBar.setVisibility(View.INVISIBLE);
            mNextButton.setVisibility(View.VISIBLE);
            return;
        }else if(time.isEmpty()){
            Toast.makeText(DateTime.this, "Select Time", Toast.LENGTH_SHORT).show();
            mProgressBar.setVisibility(View.INVISIBLE);
            mNextButton.setVisibility(View.VISIBLE);
            return;
        }else{
            Intent intent = new Intent(DateTime.this, .class);
            intent.putExtra("departure_station", departure_station);
            intent.putExtra("destination_station", destination_station);
            intent.putExtra("route", route);
            intent.putExtra("price", price);
            intent.putExtra("date", date);
            intent.putExtra("time", time);
            startActivity(intent);
            finish();
        }
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