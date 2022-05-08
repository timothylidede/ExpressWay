package com.savala.expressway.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.savala.expressway.fragment.AccountFragment;
import com.savala.expressway.fragment.BookingsFragment;
import com.savala.expressway.fragment.HomeFragment;

public class HomePagerAdapter extends FragmentPagerAdapter {
    public HomePagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return BookingsFragment.create();
            case 1:
                return HomeFragment.create();
            case 2:
                return AccountFragment.create();
        }

        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}

