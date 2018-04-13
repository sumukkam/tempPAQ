package com.example.k3vn19.paq.screen.main;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by k3vn19 on 1/29/2018.
 *
 */

public class MainViewPageAdapter extends FragmentPagerAdapter{

    private Fragment[] fragments;
    private String[] tabTitles;

    public MainViewPageAdapter(FragmentManager fm, Fragment[] fragments, String[] tabTitles) {
        super(fm);
        this.fragments = fragments;
        this.tabTitles = tabTitles;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

}
