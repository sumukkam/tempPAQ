package com.example.k3vn19.paq.screen.timer.screens.timerMain.controller;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.k3vn19.paq.screen.timer.screens.timerMain.view.TimerMainView;
import com.example.k3vn19.paq.screen.timer.screens.timerMain.view.TimerMainViewInterface.TimerMainViewListener;
import com.example.k3vn19.paq.screen.timer.view.TimerViewInterface;
import com.example.k3vn19.paq.screen.timer.view.TimerViewInterface.TimerViewListener;
import com.example.k3vn19.paq.utils.database.AlarmEntity;


/**
 * Created by k3vn19 on 1/29/2018.
 *
 * This class is the controller for TimerMain. It handles the logic of getting the user input from
 * the view and then passing it to TimerInProgressFragment to display timer animation.
 */

public class TimerMainFragment extends Fragment implements TimerMainViewListener {

    private TimerMainView mView;
    private TimerViewListener mListener;

    //Flag is true when TimerFragment is shown
    private boolean isScreenShow = false;

    public void setupFragment(TimerViewListener listener){
        this.mListener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        mView = new TimerMainView(inflater, container, this, getContext());

        return mView.getRootView();
    }

    public void setIsScreenShow(boolean isScreenShow){
        this.isScreenShow = isScreenShow;
    }

    @Override
    public void setTimer(){
        //Get the relevant input from the View.
        int hour = mView.getHour();
        int min = mView.getMinute();
        int intensity = mView.getIntensity();
        int seqLength = mView.getSeqLength();

        //Default parameters
        boolean[] week = new boolean[7];
        for(int i = 0; i < 7; i++){
            week[i] = true;
        }

        AlarmEntity alarmEntity = new AlarmEntity(hour, min, true, week, 0,
                0, seqLength, intensity);

        //TODO - save this alarm entity and also send it via BLE

        //Move to next fragment to display timer animation
        mListener.moveToInProgress(hour, min); //pass alarm entity here
    }

    @Override
    public void onResume() {
        super.onResume();

        //Set NumberPicker values back to 0.
        mView.resetNumberPicker();
    }

} //end of class
