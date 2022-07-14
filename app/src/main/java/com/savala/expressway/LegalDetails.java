package com.savala.expressway;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LegalDetails extends AppCompatActivity {
    private TextView mExpress, mWay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_legal_details);

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