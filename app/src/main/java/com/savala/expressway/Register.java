package com.savala.expressway;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.method.PasswordTransformationMethod;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Register extends AppCompatActivity {

    //widgets

    private ImageView mBack;

    private TextView mSign, mIn, mInfo, mFirstname, mSecondname, mEmail, mPass, mForgotPass;

    private EditText mEmailText, mFirstText, mSecondText, mPassText;

    private ImageView mSeePass, mUnseePass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register);

        //init

        mBack = findViewById(R.id.back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mSign = findViewById(R.id.sign_title);
        mIn = findViewById(R.id.in_title);
        mInfo = findViewById(R.id.info_title);
        mFirstname = findViewById(R.id.firstname_title);
        mSecondname = findViewById(R.id.secondname_title);
        mEmail = findViewById(R.id.email_title);
        mPass = findViewById(R.id.pass_title);
        mForgotPass = findViewById(R.id.forgot_pass);

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Poppins-SemiBold.ttf");
        Typeface tf2 = Typeface.createFromAsset(getAssets(), "fonts/Poppins-Regular.ttf");

        mSign.setTypeface(tf);
        mIn.setTypeface(tf);
        mInfo.setTypeface(tf2);
        mFirstname.setTypeface(tf);
        mSecondname.setTypeface(tf);
        mEmail.setTypeface(tf);
        mPass.setTypeface(tf);
        mForgotPass.setTypeface(tf);

        mFirstText = findViewById(R.id.firstname_text);
        mSecondText = findViewById(R.id.secondname_text);
        mEmailText = findViewById(R.id.email_text);
        mPassText = findViewById(R.id.pass_text);

        mSeePass = findViewById(R.id.see_pass);
        mUnseePass = findViewById(R.id.unsee_pass);

        mSeePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSeePass.setVisibility(View.INVISIBLE);
                mUnseePass.setVisibility(View.VISIBLE);
                mPassText.setTransformationMethod(null);
            }
        });

        mUnseePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mUnseePass.setVisibility(View.INVISIBLE);
                mSeePass.setVisibility(View.VISIBLE);
                mPassText.setTransformationMethod(new PasswordTransformationMethod());
            }
        });
    }
}