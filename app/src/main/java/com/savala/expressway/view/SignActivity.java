package com.savala.expressway.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.savala.expressway.R;

public class SignActivity extends AppCompatActivity {

    //widgets
    private TextView mExpress, mWay, mSlogan2, mLoginTitle, mRegisterTitle;

    private CardView mLoginButton, mRegisterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_sign);

        //init widgets
        mExpress = findViewById(R.id.express_title);
        mWay = findViewById(R.id.way_title);
        mSlogan2 = findViewById(R.id.slogan2);
        mLoginTitle = findViewById(R.id.login_title);
        mRegisterTitle = findViewById(R.id.register_title);

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Poppins-SemiBold.ttf");
        Typeface tf2 = Typeface.createFromAsset(getAssets(), "fonts/Poppins-Regular.ttf");
        mExpress.setTypeface(tf);
        mWay.setTypeface(tf);
        mSlogan2.setTypeface(tf);
        mLoginTitle.setTypeface(tf2);
        mRegisterTitle.setTypeface(tf2);

        mRegisterButton = findViewById(R.id.register_button);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignActivity.this, Register.class);
                startActivity(intent);
            }
        });

        mLoginButton = findViewById(R.id.login_button);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignActivity.this, Login.class);
                startActivity(intent);
            }
        });
    }
}