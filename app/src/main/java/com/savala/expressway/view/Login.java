package com.savala.expressway.view;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.method.PasswordTransformationMethod;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.savala.expressway.R;

public class Login extends AppCompatActivity {

    //widgets
    private ImageView mBack;

    private TextView mSign, mIn, mInfo, mEmail, mPass, mForgotPass;

    private EditText mLoginEmail, mLoginPassword;

    private CardView mLoginButton;

    private ProgressBar mProgressBar;

    private ImageView mSeePass, mUnseePass;

    //firebase
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);

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
        mEmail = findViewById(R.id.email_title);
        mPass = findViewById(R.id.pass_title);
        mForgotPass = findViewById(R.id.forgot_pass);
        mLoginButton = findViewById(R.id.login_button);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Poppins-SemiBold.ttf");
        Typeface tf2 = Typeface.createFromAsset(getAssets(), "fonts/Poppins-Regular.ttf");

        mSign.setTypeface(tf);
        mIn.setTypeface(tf);
        mInfo.setTypeface(tf2);
        mEmail.setTypeface(tf);
        mPass.setTypeface(tf);
        mForgotPass.setTypeface(tf);

        SpannableString content = new SpannableString("Forgot Password?");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        mForgotPass.setText(content);

        mForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, ForgotPassword.class);
                startActivity(intent);
            }
        });

        mLoginEmail = findViewById(R.id.email_text);
        mLoginPassword = findViewById(R.id.pass_text);

        mSeePass = findViewById(R.id.see_pass);
        mUnseePass = findViewById(R.id.unsee_pass);

        mSeePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSeePass.setVisibility(View.INVISIBLE);
                mUnseePass.setVisibility(View.VISIBLE);
                mLoginPassword.setTransformationMethod(null);
            }
        });

        mUnseePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mUnseePass.setVisibility(View.INVISIBLE);
                mSeePass.setVisibility(View.VISIBLE);
                mLoginPassword.setTransformationMethod(new PasswordTransformationMethod());
            }
        });

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });
    }

    private void loginUser(){
        String emailAddress = mLoginEmail.getText().toString().trim();
        String password = mLoginPassword.getText().toString().trim();

        if(emailAddress.isEmpty()){
            mLoginEmail.setError("Key in email address");
            mLoginEmail.requestFocus();
            return;
        }else if(password.isEmpty()){
            mLoginPassword.setError("Key in password");
            mLoginPassword.requestFocus();
            return;
        }else if(emailAddress.isEmpty() && password.isEmpty()){
            Toast.makeText(Login.this, "Fill in all the fields", Toast.LENGTH_SHORT).show();
        }else{
            mProgressBar.setVisibility(View.VISIBLE);
            mLoginButton.setVisibility(View.INVISIBLE);

            mAuth.signInWithEmailAndPassword(emailAddress, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d(TAG, "onComplete: " + task.isSuccessful());

                            if (!task.isSuccessful()){
                                Toast.makeText(Login.this
                                        , "Enter valid email address"
                                        , Toast.LENGTH_SHORT).show();
                                mProgressBar.setVisibility(View.GONE);
                                mLoginButton.setVisibility(View.VISIBLE);
                            }
                            else{
                                if(mAuth.getCurrentUser().isEmailVerified()){
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Intent intent = new Intent(Login.this, HomeActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();
                                }else{
                                    Toast.makeText(Login.this, "Verify your Email Address", Toast.LENGTH_LONG).show();
                                }
                                mLoginButton.setVisibility(View.VISIBLE);
                                mProgressBar.setVisibility(View.GONE);
                            }
                        }
                    });
        }
    }
}