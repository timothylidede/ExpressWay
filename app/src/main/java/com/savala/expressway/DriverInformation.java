package com.savala.expressway;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class DriverInformation extends AppCompatActivity{
    private TextView mExpress, mWay;

    private CardView mNextButton;

    private String[] manufacturers = {"Mercedes-Benz AG", "Iveco S.P.A", "MAN SE", "AB Volvo",
            "Scania AB", "EvoBus GmbH", "Temsa Europe NV", "Neoman Bus GmbH",
            "Alexander Dennis Ltd", "Solaris Bus & Coach SA."};

    private String[] years= {"2022", "2021", "2020", "2019", "2018", "2017", "2016", "2015",
            "2014", "2013", "2012", "2011", "2010", "2009", "2008", "2007", "2006", "2005", "2004",
            "2003", "2002", "2001", "2000", "1999", "1998", "1997"};

    private AutoCompleteTextView autoCompleteTextView;
    private AutoCompleteTextView autoCompleteYear;

    ArrayAdapter<String> adapterItems;
    ArrayAdapter<String> adapterYears;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_driver_information);

        mNextButton = (CardView) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DriverInformation.this, LegalDetails.class);
                startActivity(intent);
            }
        });

        autoCompleteTextView = findViewById(R.id.autoComplete_text);
        adapterItems = new ArrayAdapter<String>(this, R.layout.manufacturer_list, manufacturers);
        autoCompleteTextView.setAdapter(adapterItems);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                String manufacturer = parent.getItemAtPosition(position).toString();
            }
        });

        autoCompleteYear = findViewById(R.id.autoComplete_year);
        adapterYears = new ArrayAdapter<String>(this, R.layout.year_list, years);
        autoCompleteYear.setAdapter(adapterYears);
        autoCompleteYear.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                String year = parent.getItemAtPosition(position).toString();
            }
        });

        mExpress = findViewById(R.id.express_title);
        mWay = findViewById(R.id.way_title);

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