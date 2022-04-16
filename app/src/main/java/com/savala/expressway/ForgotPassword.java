package com.savala.expressway;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class ForgotPassword extends AppCompatActivity {

    //widgets
    private ImageView mBack;

    private TextView mReset, mPass, mInfo, mEmail, mForgotPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_forgot_password);

        //init
        mBack = findViewById(R.id.back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mReset = findViewById(R.id.reset_title);
        mPass = findViewById(R.id.password_title);
        mInfo = findViewById(R.id.info_title);
        mEmail = findViewById(R.id.email_title);
        mForgotPass = findViewById(R.id.forgot_pass);

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Poppins-SemiBold.ttf");
        Typeface tf2 = Typeface.createFromAsset(getAssets(), "fonts/Poppins-Regular.ttf");

        mReset.setTypeface(tf);
        mPass.setTypeface(tf);
        mInfo.setTypeface(tf2);
        mEmail.setTypeface(tf);
        mForgotPass.setTypeface(tf2);
    }
}