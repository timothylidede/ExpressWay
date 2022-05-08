package com.savala.expressway.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.example.hundredfifty.R;
import com.savala.expressway.R;

public class AccountFragment extends BaseFragment {
    public static AccountFragment create(){
        return new AccountFragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_more;
    }

    @Override
    public void inOnCreateView(View root, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

    }
}
