package com.savala.expressway.driver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.savala.expressway.R;

public class DriverInformation extends AppCompatActivity{
    private TextView mExpress, mWay, mManufacturer, mYear;

    private EditText mFirstName, mSecondName, mNumberPlate;

    private CheckBox mCheckBox;

    private CardView mNextButton;

    private ProgressBar mProgressBar;

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

    private ImageView mBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_driver_information);

        mFirstName = findViewById(R.id.firstname_text);
        mSecondName = findViewById(R.id.secondname_text);
        mNumberPlate = findViewById(R.id.numberplate_text);

        mManufacturer = findViewById(R.id.manufacturer);
        mYear = findViewById(R.id.year_text);

        FirebaseDatabase.getInstance().getReference("User")
                .orderByChild("user_id").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot ds:snapshot.getChildren()){
                            String first_name = "" + ds.child("first_name").getValue();
                            String last_name = "" + ds.child("last_name").getValue();

                            mFirstName.setText(first_name);
                            mSecondName.setText(last_name);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        mBack = findViewById(R.id.back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        mNextButton = (CardView) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProgressBar.setVisibility(View.VISIBLE);
                mNextButton.setVisibility(View.INVISIBLE);
                next();
            }
        });

        autoCompleteTextView = findViewById(R.id.autoComplete_text);
        adapterItems = new ArrayAdapter<String>(this, R.layout.manufacturer_list, manufacturers);
        autoCompleteTextView.setAdapter(adapterItems);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                String manufacturer = parent.getItemAtPosition(position).toString();
                mManufacturer.setText(manufacturer);
            }
        });

        autoCompleteYear = findViewById(R.id.autoComplete_year);
        adapterYears = new ArrayAdapter<String>(this, R.layout.year_list, years);
        autoCompleteYear.setAdapter(adapterYears);
        autoCompleteYear.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                String year = parent.getItemAtPosition(position).toString();
                mYear.setText(year);
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
        String first_name = mFirstName.getText().toString().trim();
        String last_name = mSecondName.getText().toString().trim();
        String manufacturer = mManufacturer.getText().toString().trim();
        String year = mYear.getText().toString().trim();
        String number_plate = mNumberPlate.getText().toString().trim();

        if(first_name.isEmpty()){
            mFirstName.setError("Key in your first name");
            mFirstName.requestFocus();
            mProgressBar.setVisibility(View.INVISIBLE);
            mNextButton.setVisibility(View.VISIBLE);
            return;
        }
        if(last_name.isEmpty()){
            mSecondName.setError("Key in your last name");
            mSecondName.requestFocus();
            mProgressBar.setVisibility(View.INVISIBLE);
            mNextButton.setVisibility(View.VISIBLE);
            return;
        }else if(number_plate.isEmpty()){
            mNumberPlate.setError("Key in your bus' number plate");
            mNumberPlate.requestFocus();
            mProgressBar.setVisibility(View.INVISIBLE);
            mNextButton.setVisibility(View.VISIBLE);
            return;
        }else if(manufacturer.isEmpty()){
            Toast.makeText(DriverInformation.this, "Select Manufacturer", Toast.LENGTH_SHORT).show();
            mProgressBar.setVisibility(View.INVISIBLE);
            mNextButton.setVisibility(View.VISIBLE);
            return;
        }else if(year.isEmpty()){
            Toast.makeText(DriverInformation.this, "Select Year of manufacture", Toast.LENGTH_SHORT).show();
            mProgressBar.setVisibility(View.INVISIBLE);
            mNextButton.setVisibility(View.VISIBLE);
            return;
        }else{
            Intent intent = new Intent(DriverInformation.this, LegalDetails.class);
            intent.putExtra("first_name", first_name);
            intent.putExtra("last_name", last_name);
            intent.putExtra("bus_manufacturer", manufacturer);
            intent.putExtra("year", year);
            intent.putExtra("number_plate", number_plate);
            startActivity(intent);
            finish();

        }
    }
}