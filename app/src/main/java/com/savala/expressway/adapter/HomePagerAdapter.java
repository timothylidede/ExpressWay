package com.savala.expressway.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class HomePagerAdapter extends FragmentPagerAdapter {
    public HomePagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return SettingsFragment.create();
            case 1:
                return FireFragment.create();
            case 2:
                return MoreFragment.create();
        }

        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}

