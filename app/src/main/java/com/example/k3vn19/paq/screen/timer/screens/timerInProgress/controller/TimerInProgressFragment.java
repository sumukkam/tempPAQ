package com.example.k3vn19.paq.screen.timer.screens.timerInProgress.controller;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.k3vn19.paq.R;
import com.example.k3vn19.paq.screen.timer.screens.timerInProgress.view.TimerIPViewInterface.TimerIPViewListener;
import com.example.k3vn19.paq.screen.timer.screens.timerInProgress.view.TimerInProgressView;
import com.example.k3vn19.paq.screen.timer.view.TimerViewInterface.TimerViewListener;

/**
 * Created by k3vn19 on 2/26/2018.
 *
 * Controller for TimerInProgress MVC.
 */

public class TimerInProgressFragment extends Fragment implements TimerIPViewListener {

    private TimerInProgressView mView;
    private TimerViewListener mListener;

    private int hour;
    private int minute;

    //Flag is true when TimerFragment is shown
    private boolean isScreenShow = false;

    public void setupFragment(TimerViewListener listener, int hour, int minute){
        this.mListener = listener;

        this.hour = hour;
        this.minute = minute;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        //Drawable to pass into View
        Resources res = getResources();
        Drawable draw = res.getDrawable(R.drawable.circular);

        mView = new TimerInProgressView(inflater, container, (TimerIPViewListener)this, draw, hour, minute);

        return mView.getRootView();
    }

    public void setIsScreenShow(boolean isScreenShow){
        this.isScreenShow = isScreenShow;
    }

    @Override
    public void cancelTimer(){
        //Go back to previous fragment
        getFragmentManager().popBackStackImmediate();
    }


}
