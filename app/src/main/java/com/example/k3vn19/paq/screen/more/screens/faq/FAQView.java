package com.example.k3vn19.paq.screen.more.screens.faq;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.k3vn19.paq.R;

/**
 * Created by k3vn19 on 2/5/2018.
 *
 * View for FAQ.
 */

public class FAQView {
    private View mRootView;

    public FAQView(LayoutInflater inflater, ViewGroup container){
        mRootView = inflater.inflate(R.layout.view_faq, container, false);
    }

    public View getRootView() {
        return mRootView;
    }
}
