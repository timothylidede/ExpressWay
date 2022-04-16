package com.savala.expressway;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

public class StartActivity extends AppCompatActivity {

    //widgets
    private TextView mExpress, mWay, mSlogan1, mSlogan2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_start);

        //init widgets
        mExpress = findViewById(R.id.express_title);
        mWay = findViewById(R.id.way_title);
        mSlogan1 = findViewById(R.id.slogan1);
        mSlogan2 = findViewById(R.id.slogan2);

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Poppins-SemiBold.ttf");
        mExpress.setTypeface(tf);
        mWay.setTypeface(tf);
        mSlogan1.setTypeface(tf);
        mSlogan2.setTypeface(tf);
    }
}