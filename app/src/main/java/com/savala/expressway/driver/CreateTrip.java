package com.savala.expressway.driver;

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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.savala.expressway.R;

public class CreateTrip extends AppCompatActivity {
    private TextView mExpress, mWay, mDeparture, mDestination;

    private CardView mNextButton;

    private ProgressBar mProgressBar;

    private AutoCompleteTextView autoCompleteDeparture, autoCompleteDestination;

    private String[] departure = {"Mlolongo", "Standard Gauge Railway", "Jomo Kenyatta International Airport",
            "Eastern Bypass", "Southern Bypass", "Capital Centre", "Haile Selassie Avenue", "Museum Hill",
            "Westlands", "James Gichuru Road"};

    private String[] destination = {"Mlolongo", "Standard Gauge Railway", "Jomo Kenyatta International Airport",
            "Eastern Bypass", "Southern Bypass", "Capital Centre", "Haile Selassie Avenue", "Museum Hill",
            "Westlands", "James Gichuru Road"};

    ArrayAdapter<String> adapterDeparture, adapterDestination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_create_trip);

        mNextButton = (CardView) findViewById(R.id.next_button);

        mProgressBar = findViewById(R.id.progressBar);

        mDeparture = (TextView) findViewById(R.id.departure_text);
        autoCompleteDeparture = findViewById(R.id.autoComplete_departure);
        adapterDeparture = new ArrayAdapter<String>(this, R.layout.list, departure);
        autoCompleteDeparture.setAdapter(adapterDeparture);
        autoCompleteDeparture.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                String departure = parent.getItemAtPosition(position).toString();
                mDeparture.setText(departure);
            }
        });

        mDestination = (TextView) findViewById(R.id.destination_text);
        autoCompleteDestination = findViewById(R.id.autoComplete_destination);
        adapterDestination = new ArrayAdapter<String>(this, R.layout.list, destination);
        autoCompleteDestination.setAdapter(adapterDestination);
        autoCompleteDestination.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                String destination = parent.getItemAtPosition(position).toString();
                mDestination.setText(destination);
            }
        });

        mExpress = findViewById(R.id.express_title);
        mWay = findViewById(R.id.way_title);
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Poppins-SemiBold.ttf");
        Typeface tf2 = Typeface.createFromAsset(getAssets(), "fonts/Poppins-Regular.ttf");

        mExpress.setTypeface(tf);
        mWay.setTypeface(tf);

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProgressBar.setVisibility(View.VISIBLE);
                mNextButton.setVisibility(View.INVISIBLE);
                next();
            }
        });
    }

    private void next() {
        String departure_station = mDeparture.getText().toString().trim();
        String destination_station = mDestination.getText().toString().trim();

        if(departure_station.isEmpty()){
            Toast.makeText(CreateTrip.this, "Select Departure Station", Toast.LENGTH_SHORT).show();
            mProgressBar.setVisibility(View.INVISIBLE);
            mNextButton.setVisibility(View.VISIBLE);
            return;
        }else if(destination_station.isEmpty()){
            Toast.makeText(CreateTrip.this, "Select Destination Station", Toast.LENGTH_SHORT).show();
            mProgressBar.setVisibility(View.INVISIBLE);
            mNextButton.setVisibility(View.VISIBLE);
            return;
        }else{
            Intent intent = new Intent(CreateTrip.this, Price.class);
            intent.putExtra("departure_station", departure_station);
            intent.putExtra("destination_station", destination_station);
            startActivity(intent);
            finish();
        }
    }
}