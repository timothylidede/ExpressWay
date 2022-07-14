package com.savala.expressway;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.savala.expressway.R;

public class ForgotPassword extends AppCompatActivity {

    //widgets
    private ImageView mBack;

    private TextView mReset, mPass, mInfo, mEmail, mForgotPass;
    private EditText mResetPassEmail;

    private ProgressBar mProgressBar;
    FirebaseAuth auth;

    private CardView mResetPassButton;

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

        mResetPassEmail = (EditText) findViewById(R.id.email_text);
        auth = FirebaseAuth.getInstance();
        mResetPassButton = findViewById(R.id.reset_button);
        mResetPassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });

        mReset = findViewById(R.id.reset_title);
        mPass = findViewById(R.id.password_title);
        mInfo = findViewById(R.id.info_title);
        mEmail = findViewById(R.id.email_title);
        mForgotPass = findViewById(R.id.forgot_pass);
        mProgressBar = findViewById(R.id.progressBar);

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Poppins-SemiBold.ttf");
        Typeface tf2 = Typeface.createFromAsset(getAssets(), "fonts/Poppins-Regular.ttf");

        mReset.setTypeface(tf);
        mPass.setTypeface(tf);
        mInfo.setTypeface(tf2);
        mEmail.setTypeface(tf);
        mForgotPass.setTypeface(tf2);
    }

    private void resetPassword() {
        String email = mResetPassEmail.getText().toString().trim();

        if(email.isEmpty()){
            Toast.makeText(ForgotPassword.this, "Provide email", Toast.LENGTH_SHORT).show();
            mResetPassEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(ForgotPassword.this, "Provide a valid email", Toast.LENGTH_SHORT).show();
            mResetPassEmail.requestFocus();
            return;
        }

        mProgressBar.setVisibility(View.VISIBLE);
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ForgotPassword.this, "We have sent you an email to reset your password", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ForgotPassword.this, "Something went wrong. Try again", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}