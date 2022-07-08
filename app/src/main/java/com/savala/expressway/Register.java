package com.savala.expressway;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.savala.expressway.model.User;

import java.util.HashMap;

public class Register extends AppCompatActivity {

    //widgets

    private ImageView mBack;

    private TextView mSign, mIn, mInfo, mFirstName, mLastName, mEmailAddress, mPassword, mForgotPass;

    private EditText mEmailText, mFirstText, mSecondText, mPassText;

    private ImageView mSeePass, mUnseePass;

    private ProgressBar mProgressBar;

    private CardView mSignUp;

    //firebase
    private FirebaseAuth mAuth;
    private DatabaseReference myUserRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register);

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.layout);
        AnimationDrawable animationDrawable = (AnimationDrawable) layout.getBackground();
        animationDrawable.setEnterFadeDuration(3000);
        animationDrawable.setExitFadeDuration(3000);
        animationDrawable.start();

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
        mFirstName = findViewById(R.id.firstname_title);
        mLastName = findViewById(R.id.secondname_title);
        mEmailAddress = findViewById(R.id.email_title);
        mPassword = findViewById(R.id.pass_title);
        mForgotPass = findViewById(R.id.forgot_pass);
        mSignUp = (CardView) findViewById(R.id.register_button);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        myUserRef = FirebaseDatabase.getInstance().getReference("User");

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Poppins-SemiBold.ttf");
        Typeface tf2 = Typeface.createFromAsset(getAssets(), "fonts/Poppins-Regular.ttf");

        mSign.setTypeface(tf);
        mIn.setTypeface(tf);
        mInfo.setTypeface(tf2);
        mFirstName.setTypeface(tf);
        mLastName.setTypeface(tf);
        mEmailAddress.setTypeface(tf);
        mPassword.setTypeface(tf);
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

        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
    }

    private void registerUser(){
        String firstName = mFirstText.getText().toString().trim();
        String lastName = mSecondText.getText().toString().trim();
        String emailAddress = mEmailText.getText().toString().trim();
        String password = mPassText.getText().toString().trim();

        if(firstName.isEmpty()){
            mFirstText.setError("First name is required");
            mFirstText.requestFocus();
            return;
        }else if(lastName.isEmpty()){
            mSecondText.setError("Last name is required");
            mSecondText.requestFocus();
            return;
        }else if(emailAddress.isEmpty()){
            mEmailText.setError("Email is required");
            mEmailText.requestFocus();
            return;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()){
            mEmailText.setError("Provide a valid email address");
            mEmailText.requestFocus();
            return;
        }else if(password.isEmpty()){
            mPassText.setError("You need to set a password");
            mPassText.requestFocus();
            return;
        }else{
            if(passIsValid(password)){
                mProgressBar.setVisibility(View.VISIBLE);
                mSignUp.setVisibility(View.INVISIBLE);

                mAuth.createUserWithEmailAndPassword(emailAddress, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Log.d(TAG, "onComplete: " + task.isSuccessful());
                                if (!task.isSuccessful()){
                                    Toast.makeText(Register.this
                                            , "Enter valid email address"
                                            , Toast.LENGTH_SHORT).show();
                                    mProgressBar.setVisibility(View.GONE);
                                    mSignUp.setVisibility(View.VISIBLE);
                                }else if(task.isSuccessful()){
                                    String userID = mAuth.getCurrentUser().getUid();

                                    User user = new User(firstName, lastName, emailAddress, userID);

                                    String departure_station = "Enter departure station";
                                    String destination_station = "Enter destination station";
                                    String timestamp = "" + System.currentTimeMillis();
                                    String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();

                                    HashMap<String, Object> hashMap = new HashMap<>();
                                    hashMap.put("booking_id", timestamp);
                                    hashMap.put("departure_station", departure_station);
                                    hashMap.put("user_id", user_id);
                                    hashMap.put("destination_station", destination_station);

                                    FirebaseDatabase.getInstance().getReference("ResumeBookings")
                                            .child(user_id)
                                            .setValue(hashMap);

                                    myUserRef.child(userID)
                                            .setValue(user)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful()){
                                                        Toast.makeText(Register.this
                                                                , "Sign Up Success"
                                                                , Toast.LENGTH_SHORT).show();
                                                        mProgressBar.setVisibility(View.INVISIBLE);
                                                        mSignUp.setVisibility(View.VISIBLE);

                                                        mAuth.getCurrentUser().sendEmailVerification()
                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        if(task.isSuccessful()){
                                                                            Toast.makeText(Register.this
                                                                                    , "Almost done! Click the link we have sent in your email inbox"
                                                                                    , Toast.LENGTH_LONG).show();
                                                                            Intent intent = new Intent(
                                                                                    Register.this,
                                                                                    Login.class
                                                                            );
                                                                            finish();
                                                                        }else{
                                                                            Toast.makeText(Register.this
                                                                                    , "Please try again"
                                                                                    , Toast.LENGTH_LONG).show();
                                                                        }
                                                                    }
                                                                });
                                                    }else{
                                                        Toast.makeText(Register.this
                                                                , "Sign Up Failed"
                                                                , Toast.LENGTH_LONG).show();
                                                        mProgressBar.setVisibility(View.INVISIBLE);
                                                        mSignUp.setVisibility(View.VISIBLE);
                                                    }
                                                }
                                            });
                                }
                            }
                        });
            }
        }
    }

    public boolean passIsValid(String password) {
        // for checking if password length is between 6 and 15
        if (!((password.length() >= 6))
                && (password.length() <= 15)){
            mPassText.setError("Password must be between 6 and 15 characters");
            mPassText.requestFocus();
            return false;
        }

        // to check space
        if (password.contains(" ")) {
            mPassText.setError("Password must not have a space");
            mPassText.requestFocus();
            return false;
        }

        // if all conditions fails
        return true;
    }
}