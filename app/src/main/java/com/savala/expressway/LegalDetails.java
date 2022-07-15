package com.savala.expressway;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LegalDetails extends AppCompatActivity {
    private TextView mExpress, mWay;

    private CardView mNextButton;

    private EditText mNationalID, mDriverLicense;

    private String first_name, last_name, bus_manufacturer, year, number_plate;

    private ImageView mBack;

    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_legal_details);

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        mBack = findViewById(R.id.back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mNationalID = findViewById(R.id.nationalID_text);
        mDriverLicense = findViewById(R.id.driverlicense_text);

        Intent intent = getIntent();
        first_name = intent.getStringExtra("first_name");
        last_name = intent.getStringExtra("last_name");
        bus_manufacturer = intent.getStringExtra("bus_manufacturer");
        year = intent.getStringExtra("year");
        number_plate = intent.getStringExtra("number_plate");

        mNextButton = (CardView) findViewById(R.id.next_button);
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

    private void next() {
        String national_id = mNationalID.getText().toString().trim();
        String dl_ref_no = mDriverLicense.getText().toString().trim();

        if(national_id.isEmpty()){
            mNationalID.setError("Key in your national ID");
            mNationalID.requestFocus();
            mProgressBar.setVisibility(View.INVISIBLE);
            mNextButton.setVisibility(View.VISIBLE);
            return;
        }else if(dl_ref_no.isEmpty()){
            mDriverLicense.setError("Key in your first name");
            mDriverLicense.requestFocus();
            mProgressBar.setVisibility(View.INVISIBLE);
            mNextButton.setVisibility(View.VISIBLE);
            return;
        }else{
            Intent intent = new Intent(LegalDetails.this, Documents.class);
            intent.putExtra("first_name", first_name);
            intent.putExtra("last_name", last_name);
            intent.putExtra("bus_manufacturer", bus_manufacturer);
            intent.putExtra("year", year);
            intent.putExtra("number_plate", number_plate);
            intent.putExtra("national_id", national_id);
            intent.putExtra("dl_ref_no", dl_ref_no);
            startActivity(intent);
            finish();
        }
    }
}