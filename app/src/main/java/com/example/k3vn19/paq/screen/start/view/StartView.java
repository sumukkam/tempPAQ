package com.example.k3vn19.paq.screen.start.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.k3vn19.paq.R;

/**
 * Created by k3vn19 on 1/29/2018.
 *
 * View for Start.
 */

public class StartView implements StartViewInterface{

    private View mRootView;

    public StartView(LayoutInflater inflater, ViewGroup container){
        mRootView = inflater.inflate(R.layout.view_start, container, false);
    }

    @Override
    public View getRootView() { return mRootView;}
}
