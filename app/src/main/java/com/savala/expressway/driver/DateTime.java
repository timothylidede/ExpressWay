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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.savala.expressway.R;

import java.util.Calendar;
import java.util.HashMap;

public class DateTime extends AppCompatActivity {

    private ImageView mBack;

    private TextView mDate, mTime, mDayText;

    private CalendarView mCalendarView;

    private TextView mExpress, mWay;

    private AutoCompleteTextView autoCompleteTime;

    private String[] time = {"12:00 am", "01:00 am", "02:00 am", "03:00 am", "04:00 am", "05:00 am",
            "06:00 am", "07:00 am", "08:00 am", "09:00 am", "10:00 am", "11:00 am", "12:00 pm",
            "1:00 pm", "02:00 pm", "03:00 pm", "04:00 pm", "05:00 pm",
            "06:00 pm", "07:00 pm", "08:00 pm", "09:00 pm", "10:00 pm", "11:00 pm"};

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
        mDayText = findViewById(R.id.day);

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
                String mMonth = "" + getMonthFormat(month);
                String mYear = "" + year;

                mDate.setText(mDay + " " + mMonth + " " + mYear);
                mDayText.setText(mDay);
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
        String day = mDayText.getText().toString().trim();

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

            FirebaseDatabase.getInstance().getReference("Driver")
                    .orderByChild("user_id").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot ds: snapshot.getChildren()){
                                String number_plate = "" + ds.child("number_plate").getValue();
                                String number_of_seats = "" + ds.child("number_of_seats").getValue();
                                String rating = "" + ds.child("rating").getValue();
                                String bus_manufacturer = "" + ds.child("bus_manufacturer").getValue();

                                HashMap<String, Object> hashMap = new HashMap<>();
                                hashMap.put("departure_station", departure_station);
                                hashMap.put("destination_station", destination_station);
                                hashMap.put("route", route);
                                hashMap.put("price", price);
                                hashMap.put("date", date);
                                hashMap.put("time", time);
                                hashMap.put("day", day);
                                hashMap.put("number_plate", number_plate);
                                hashMap.put("seats", number_of_seats);
                                hashMap.put("rating", rating);
                                hashMap.put("bus_manufacturer", bus_manufacturer);

                                FirebaseDatabase.getInstance().getReference("Bus")
                                        .child(number_plate)
                                        .setValue(hashMap)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    Toast.makeText(DateTime.this, "Trip successfully created. Wait for passengers to start booking", Toast.LENGTH_LONG).show();
                                                    Toast.makeText(DateTime.this, "To check progress of the trip booking, go to \"Ongoing Trips\"", Toast.LENGTH_LONG).show();
                                                    finish();
                                                }else{
                                                    Toast.makeText(DateTime.this, "Something went wrong. Please try again", Toast.LENGTH_LONG).show();
                                                    mProgressBar.setVisibility(View.INVISIBLE);
                                                    mNextButton.setVisibility(View.VISIBLE);

                                                }
                                            }
                                        });

                                FirebaseDatabase.getInstance().getReference("PendingTrip")
                                        .child(number_plate)
                                        .setValue(hashMap);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
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