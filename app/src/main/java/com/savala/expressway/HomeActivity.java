package com.savala.expressway;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.savala.expressway.view.HomeBottomTabsView;

public class HomeActivity extends AppCompatActivity {

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();

        final View background = findViewById(R.id.am_background_view);
        ViewPager viewPager = (ViewPager) findViewById(R.id.am_view_pager);
        HomePagerAdapter adapter = new HomePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        HomeBottomTabsView fomBottomTabsView = (HomeBottomTabsView) findViewById(R.id.am_home_tabs);
        fomBottomTabsView.setUpWithViewPager(viewPager);

        viewPager.setCurrentItem(1);

        final int colorWhite = ContextCompat.getColor(this, R.color.white);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {
                if(position == 0){
                    background.setBackgroundColor(colorWhite);
                    background.setAlpha(1-positionOffset);
                }else if(position == 1){
                    background.setBackgroundColor(colorWhite);
                    background.setAlpha(positionOffset);
                }
            }

            @Override
            public void onPageSelected(int position) {


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /* Firebase */
    private void checkCurrentUser(){
        Log.d(TAG, "checkCurrentUser: checking if user is logged in");
        FirebaseUser user = mAuth.getCurrentUser();
        if(user == null){
            Intent intent = new Intent(HomeActivity.this, SignActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkCurrentUser();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}