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

import com.savala.expressway.R;

public class BookingsFragment extends BaseFragment{
    private CardView mContinueButton;
    private TextView mExpress, mWay;
    public static BookingsFragment create(){
        return new BookingsFragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_bookings;
    }

    @Override
    public void inOnCreateView(View root, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mExpress = root.findViewById(R.id.express_title);
        mWay = root.findViewById(R.id.way_title);
        mContinueButton = (CardView) root.findViewById(R.id.continue_button);
        mContinueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent()
            }
        });

        RelativeLayout top_layout = (RelativeLayout) root.findViewById(R.id.top_layout);
        AnimationDrawable top_layout1 = (AnimationDrawable) top_layout.getBackground();
        top_layout1.setEnterFadeDuration(3000);
        top_layout1.setExitFadeDuration(3000);
        top_layout1.start();

        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Poppins-SemiBold.ttf");
        Typeface tf2 = Typeface.createFromAsset(getContext().getAssets(), "fonts/Poppins-Regular.ttf");

        mExpress.setTypeface(tf);
        mWay.setTypeface(tf);
    }
}
