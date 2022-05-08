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

import com.savala.expressway.DepartureStation;
import com.savala.expressway.R;

public class HomeFragment extends BaseFragment{

    //widgets
    private TextView mSearchTitle, mWhenTitle;
    private TextView mExpress, mWay;

    private CardView mDepartureStation;

    public static HomeFragment create(){
        return new HomeFragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    public void inOnCreateView(View root, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RelativeLayout layout = (RelativeLayout) root.findViewById(R.id.layout);
        AnimationDrawable animationDrawable = (AnimationDrawable) layout.getBackground();
        animationDrawable.setEnterFadeDuration(3000);
        animationDrawable.setExitFadeDuration(3000);
        animationDrawable.start();

        RelativeLayout layout1 = (RelativeLayout) root.findViewById(R.id.layout1);
        AnimationDrawable animationDrawable1 = (AnimationDrawable) layout1.getBackground();
        animationDrawable1.setEnterFadeDuration(3000);
        animationDrawable1.setExitFadeDuration(3000);
        animationDrawable1.start();

        RelativeLayout top_layout = (RelativeLayout) root.findViewById(R.id.top_layout);
        AnimationDrawable top_layout1 = (AnimationDrawable) top_layout.getBackground();
        top_layout1.setEnterFadeDuration(3000);
        top_layout1.setExitFadeDuration(3000);
        top_layout1.start();

        mSearchTitle = (TextView) root.findViewById(R.id.search_title);
        mWhenTitle = (TextView) root.findViewById(R.id.when_title);
        mExpress = root.findViewById(R.id.express_title);
        mWay = root.findViewById(R.id.way_title);

        mDepartureStation = (CardView) root.findViewById(R.id.from_destination);

        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Poppins-SemiBold.ttf");
        Typeface tf2 = Typeface.createFromAsset(getContext().getAssets(), "fonts/Poppins-Regular.ttf");
        mSearchTitle.setTypeface(tf);
        mWhenTitle.setTypeface(tf2);
        mExpress.setTypeface(tf);
        mWay.setTypeface(tf);

        mDepartureStation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), DepartureStation.class);
                startActivity(intent);
            }
        });
    }
}
