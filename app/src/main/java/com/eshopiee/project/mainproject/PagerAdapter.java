package com.eshopiee.project.mainproject;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {

    int mNoOfTabs;
    public PagerAdapter(FragmentManager fm, int NumberOfTabs)
    {
        super(fm);
        this.mNoOfTabs = NumberOfTabs;

    }
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                home home = new home();
                return home;
            case 1:
                nearme nearme = new nearme();
                return nearme;
            case 2:
                profile profile = new profile();
                return profile;
             default:
                 return null;


        }

    }

    @Override
    public int getCount() {
        return mNoOfTabs;
    }
}
