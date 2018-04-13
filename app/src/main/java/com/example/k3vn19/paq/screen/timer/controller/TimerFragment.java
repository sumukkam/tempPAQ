package com.example.k3vn19.paq.screen.timer.controller;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.k3vn19.paq.screen.timer.screens.timerInProgress.controller.TimerInProgressFragment;
import com.example.k3vn19.paq.screen.timer.screens.timerMain.controller.TimerMainFragment;
import com.example.k3vn19.paq.screen.timer.view.TimerView;
import com.example.k3vn19.paq.screen.timer.view.TimerViewInterface;

/**
 * Created by k3vn19 on 1/29/2018.
 *
 * Controller for Timer MVC.
 */

public class TimerFragment extends Fragment implements TimerViewInterface.TimerViewListener{

    private TimerView mView;
    private TimerMainFragment timerMainFragment;
    private TimerInProgressFragment timerInProgressFragment;

    //This flag is true when this screen is shown
    private boolean isScreenShow = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceBundle){
        Log.d("TimerFragment", "OnCreateView()=============");

        mView = new TimerView(inflater, container);

        //set initial fragment
        timerMainFragment = new TimerMainFragment();
        timerMainFragment.setupFragment(this);
        mView.changeContent(getChildFragmentManager(), timerMainFragment, false);

        return mView.getRootView();
    }

    /**
     * This method handles whether this screen is going to be displayed or not
     *
     */
    public void isScreenShow(boolean isScreenShow) {
        this.isScreenShow = isScreenShow;
        if(timerMainFragment != null) {
            timerMainFragment.setIsScreenShow(isScreenShow);
        }
    }

    @Override
    public void moveToInProgress(int hour, int minute){
        //set initial fragment
        timerInProgressFragment= new TimerInProgressFragment();
        timerInProgressFragment.setupFragment(this, hour, minute);

        mView.changeContent(getChildFragmentManager(), timerInProgressFragment, true);
    }

} //end of class