package com.example.k3vn19.paq.screen.more.screens.aboutPAQ;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.k3vn19.paq.R;

/**
 * Created by k3vn19 on 2/5/2018.
 *
 * View for AboutPAQ package
 */

public class AboutPAQView {

    private View mRootView;

    public AboutPAQView(LayoutInflater inflater, ViewGroup container){
        mRootView = inflater.inflate(R.layout.view_about_paq, container, false);
    }

    public View getRootView() {
        return mRootView;
    }
}
