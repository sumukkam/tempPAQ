package com.example.k3vn19.paq.screen.stopwatch;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.k3vn19.paq.screen.stopwatch.StopwatchInterface.StopwatchListener;
/**
 * Created by k3vn19 on 1/29/2018.
 *
 */

public class StopwatchFragment extends Fragment implements StopwatchListener {

    private StopwatchView mView;

    private Boolean isScreenShow;
    private boolean hasStarted;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        mView = new StopwatchView(inflater, container, this);

        this.hasStarted = false;
        return mView.getRootView();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * This method is called when stopwatch must start. Also doubles as a pause button once stopwatch
     * starts
     * If isStart, start. If !isStart pause.
     */
    @Override
    public void startPressed() {
        if (!hasStarted) { //start timer
            Log.d("StopwatchFrag","Start pressed =========================");
            mView.startTimer();
            hasStarted = true;
        }
        else{ //pause timer
            Log.d("StopwatchFrag","pause pressed =========================");
            mView.pauseTimer();
        }
    }

    @Override
    public void resetPressed() {
        mView.resetTimer();
        hasStarted = false;
    }


    public void isScreenShow(boolean isScreenShow) {
        this.isScreenShow = isScreenShow;
    }

    public Boolean getScreenShow() {
        return isScreenShow;
    }

}
