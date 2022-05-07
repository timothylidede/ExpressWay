package com.savala.expressway.view;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

public class HomeBottomTabsView extends FrameLayout implements ViewPager.OnPageChangeListener {
    private ImageView mCenterImage;
    private ImageView mStartImage;
    private ImageView mEndImage;

    private ArgbEvaluator mrAgbEvaluator;
    private int mCenterColor;
    private int mSideColor;

    private int mEndViewsTranslationX;
    private int mIndicatorTranslationX;
    private int mCenterTranslationY;

    public HomeBottomTabsView(@NonNull Context context) {
        this(context, null);
    }

    public HomeBottomTabsView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HomeBottomTabsView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    public void setUpWithViewPager(ViewPager viewPager){
        viewPager.addOnPageChangeListener(this);

        mStartImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewPager.getCurrentItem() != 0)
                    viewPager.setCurrentItem(0);
            }
        });

        mEndImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewPager.getCurrentItem() != 2)
                    viewPager.setCurrentItem(2);
            }
        });

        mCenterImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewPager.getCurrentItem() != 1){
                    viewPager.setCurrentItem(1);
                }
            }
        });
    }

    private void init(){
        LayoutInflater.from(getContext()).inflate(R.layout.view_home_bottom_tabs, this, true);

        mCenterImage = (ImageView) findViewById(R.id.vst_center_image);
        mStartImage = (ImageView) findViewById(R.id.vst_start_image);
        mEndImage = (ImageView) findViewById(R.id.vst_end_image);

        mSideColor = ContextCompat.getColor(getContext(), R.color.white);

        mrAgbEvaluator = new ArgbEvaluator();

        mIndicatorTranslationX = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 150, getResources().getDisplayMetrics());
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if(position == 0){
            setColor(1-positionOffset);
            moveViews(1 - positionOffset);

            moveAndScaleCenter(1 - positionOffset);
            mStartImage.setColorFilter(ContextCompat.getColor(getContext(), R.color.fire), android.graphics.PorterDuff.Mode.SRC_IN);
        }else if(position == 1){
            setColor(positionOffset);
            moveViews(positionOffset);
            mCenterImage.setColorFilter(ContextCompat.getColor(getContext(), R.color.fire), android.graphics.PorterDuff.Mode.SRC_IN);
            moveAndScaleCenter(positionOffset);
        }else if(position == 2){
            setColor(1-positionOffset);
            moveViews(1 - positionOffset);
            mEndImage.setColorFilter(ContextCompat.getColor(getContext(), R.color.fire), android.graphics.PorterDuff.Mode.SRC_IN);
            moveAndScaleCenter(1 - positionOffset);
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void moveAndScaleCenter(float fractionFromCenter){
        float scale = .7f + ((1-fractionFromCenter) * .3f);

        mCenterImage.setScaleX(scale);
        mCenterImage.setScaleY(scale);

        int translation = (int) (fractionFromCenter * mCenterTranslationY);

        mCenterImage.setTranslationY(translation);
    }

    private void moveViews(float fractionFromCenter){
        mStartImage.setTranslationX(fractionFromCenter * mEndViewsTranslationX);
        mEndImage.setTranslationX(-fractionFromCenter * mEndViewsTranslationX);
    }

    private void setColor(float fractionFromCenter){
        int color = (int) mrAgbEvaluator.evaluate(fractionFromCenter, mCenterColor, mSideColor);

        mCenterImage.setColorFilter(color);
        mStartImage.setColorFilter(color);
        mEndImage.setColorFilter(color);
    }
}
