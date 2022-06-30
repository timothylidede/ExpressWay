package com.savala.expressway.driver;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.savala.expressway.R;

public class BusDetails extends AppCompatActivity {

    private ImageView mBack;
    private TextView mExpress, mWay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_details);

        mBack = findViewById(R.id.back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mExpress = findViewById(R.id.express_title);
        mWay = findViewById(R.id.way_title);
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Poppins-SemiBold.ttf");
        Typeface tf2 = Typeface.createFromAsset(getAssets(), "fonts/Poppins-Regular.ttf");
        mExpress.setTypeface(tf);
        mWay.setTypeface(tf);
    }
}