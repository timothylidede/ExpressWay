package com.savala.expressway.view;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.savala.expressway.R;

public class Register extends AppCompatActivity {

    //widgets

    private ImageView mBack;

    private TextView mSign, mIn, mInfo, mFirstName, mLastName, mEmailAddress, mPassword, mForgotPass;

    private EditText mEmailText, mFirstText, mSecondText, mPassText;

    private ImageView mSeePass, mUnseePass;

    private ProgressBar mProgressBar;

    private Button mSignUp;

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
        mSignUp = (Button) findViewById(R.id.register_button);
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
        String firstName = mFirstName.getText().toString().trim();
        String lastName = mLastName.getText().toString().trim();
        String emailAddress = mEmailAddress.getText().toString().trim();
        String password = mPassword.getText().toString().trim();

        if(firstName.isEmpty()){
            mFirstName.setError("Your first name is required");
            mFirstName.requestFocus();
            return;
        }else if(lastName.isEmpty()){
            mLastName.setError("Your last name is required");
            mLastName.requestFocus();
            return;
        }else if(emailAddress.isEmpty()){
            mEmailAddress.setError("Your email is required");
            mEmailAddress.requestFocus();
            return;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()){
            mEmailAddress.setError("Provide a valid email address");
            mEmailAddress.requestFocus();
            return;
        }else if(password.isEmpty()){
            mPassword.setError("You need to set a password");
            mPassword.requestFocus();
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

                                    myUserRef.child(userID)
                                            .setValue(user)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful()){
                                                        Toast.makeText(SignUp.this
                                                                , "Sign Up Success"
                                                                , Toast.LENGTH_LONG).show();
                                                        mProgressBar.setVisibility(View.INVISIBLE);
                                                        mSignUp.setVisibility(View.VISIBLE);
                                                    }else{
                                                        Toast.makeText(SignUp.this
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
            mPassword.setError("Password must be between 6 and 15 characters");
            mPassword.requestFocus();
            return false;
        }

        // to check space
        if (password.contains(" ")) {
            mPassword.setError("Password must not have a space");
            mPassword.requestFocus();
            return false;
        }

        // if all conditions fails
        return true;
    }
}