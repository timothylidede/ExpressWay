package com.savala.expressway.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.example.hundredfifty.R;
import com.savala.expressway.R;

public class BookingsFragment extends BaseFragment{
    public static BookingsFragment create(){
        return new BookingsFragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_settings;
    }

    @Override
    public void inOnCreateView(View root, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

    }
}
