package com.savala.expressway.fragment;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.savala.expressway.common.AboutUs;
import com.savala.expressway.common.EditProfile;
import com.savala.expressway.auth.Logout;
import com.savala.expressway.common.Offers;
import com.savala.expressway.R;
import com.savala.expressway.auth.VerifyNumber;

public class AccountFragment extends BaseFragment {

    //widgets
    private TextView mExpress, mWay, mWhenTitle;

    private CardView mLogout, mEditProfile, mVerifyNumber, mAboutUs, mOffers;

    public static AccountFragment create(){
        return new AccountFragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_account;
    }

    @Override
    public void inOnCreateView(View root, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        RelativeLayout layout = (RelativeLayout) root.findViewById(R.id.layout);
        AnimationDrawable animationDrawable = (AnimationDrawable) layout.getBackground();
        animationDrawable.setEnterFadeDuration(3000);
        animationDrawable.setExitFadeDuration(3000);
        animationDrawable.start();

        RelativeLayout top_layout = (RelativeLayout) root.findViewById(R.id.top_layout);
        AnimationDrawable top_layout1 = (AnimationDrawable) top_layout.getBackground();
        top_layout1.setEnterFadeDuration(3000);
        top_layout1.setExitFadeDuration(3000);
        top_layout1.start();

        RelativeLayout layout1 = (RelativeLayout) root.findViewById(R.id.layout1);
        AnimationDrawable animationDrawable1 = (AnimationDrawable) layout1.getBackground();
        animationDrawable1.setEnterFadeDuration(3000);
        animationDrawable1.setExitFadeDuration(3000);
        animationDrawable1.start();

        mExpress = root.findViewById(R.id.express_title);
        mWay = root.findViewById(R.id.way_title);
        mWhenTitle = (TextView) root.findViewById(R.id.when_title);

        mLogout = (CardView) root.findViewById(R.id.logout);
        mEditProfile = (CardView) root.findViewById(R.id.edit_button);
        mVerifyNumber = (CardView) root.findViewById(R.id.verify_number);
        mAboutUs = (CardView) root.findViewById(R.id.about_us);
        mOffers = (CardView) root.findViewById(R.id.offers);

        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Poppins-SemiBold.ttf");
        Typeface tf2 = Typeface.createFromAsset(getContext().getAssets(), "fonts/Poppins-Regular.ttf");

        mExpress.setTypeface(tf);
        mWay.setTypeface(tf);
        mWhenTitle.setTypeface(tf2);

        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Logout.class);
                startActivity(intent);
            }
        });

        mEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), EditProfile.class);
                startActivity(intent);
            }
        });

        mVerifyNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), VerifyNumber.class);
                startActivity(intent);
            }
        });

        mAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AboutUs.class);
                startActivity(intent);
            }
        });

        mOffers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Offers.class);
                startActivity(intent);
            }
        });

    }

}
