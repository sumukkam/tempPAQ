package com.example.k3vn19.paq.screen.stopwatch;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.k3vn19.paq.R;

/**
 * Created by k3vn19 on 3/5/2018.
 *
 * View for Stopwatch. Handles UI and updates the stopwatch TextView
 */

public class StopwatchView implements StopwatchInterface{

    private TextView mStart;    //Button to start/pause timer
    private TextView mTime;     //Holds time

    private Thread mThread;     //This thread counts the time for the stopwatch
    private View mRootView;

    private View primary;       //Primary view holds button to start/pause
    private View secondary;     //Secondary view holds button to reset

    private StopwatchListener mListener;

    private boolean bContinue = false;  //flag for while loop in Thread to continue counting
    private long currTime = 0;          //Amount of time that has passed since "start" button pressed
    private int mHours = 0;             //hours counted
    private int mMinutes = 0;           //minutes counted
    private int mSeconds = 0;           //seconds counted
    private int mCentiSec = 0;          //centiseconds

    StopwatchView(LayoutInflater inflater, ViewGroup container, StopwatchListener listener){
        mRootView = inflater.inflate(R.layout.view_stopwatch, container, false);
        Log.d("StopwatchView", "Constructor==================");

        this.mListener = listener;
        initialize();
    }

    @Override
    public View getRootView(){
        return mRootView;
    }

    /**
     * Initialize the TextView and Buttons with respective OnClickListeners.
     */
    private void initialize(){
        mTime = mRootView.findViewById(R.id.tv_stopwatch_time);

        mStart = mRootView.findViewById(R.id.tv_stopwatch_button);
        mStart.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                mListener.startPressed();
            }
        });

        TextView mReset = mRootView.findViewById(R.id.tv_stopwatch_reset);
        mReset.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                mListener.resetPressed();
            }
        });

        primary = mRootView.findViewById(R.id.stopwatch_mainLayout);
        secondary = mRootView.findViewById(R.id.stopwatch_secondaryLayout);
        secondary.setVisibility(View.GONE);
    }

    /**
     * Purpose - This method creates the thread to begin counting for the stopwatch.
     */
    void startTimer(){
        //toggle visibility and flag for while loop
        secondary.setVisibility(View.GONE);
        bContinue = true;

        //create the thread, initialize it
        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(bContinue){
                    try {
                        //Sleep the thread to mimic the behavior of a stopwatch
                        Thread.sleep(10); //pause thread for 10 milliseconds
                        currTime += 10;

                        //convert to minutes
                        long secondsPast = currTime / 1000;
                        mCentiSec = (int)(currTime - 1000*secondsPast)/10;
                        mMinutes = (int) (secondsPast / 60);
                        secondsPast -= mMinutes * 60;
                        mSeconds = (int) secondsPast;

                        //convert to hour and assign remainder to minute
                        mHours = mMinutes / 60;
                        mMinutes = mMinutes - mHours * 60;

                        Log.d("StopwatchView", "Seconds: " + mSeconds);
                        //Call post to update UI from within a thread, update hour and min TV's

                        setText();

                    }catch (InterruptedException e) {
                        //Catch any exception from sleeping the thread
                        e.printStackTrace();
                    }
                }
            }
        });

        mThread.start();

        //update button text
        String pauseString = "Pause";
        mStart.setText(pauseString);
    }


    /**
     * This method is called from the thread to continuosly update the TextView that displays amount
     * of time that has passed since start was pressed.
     */
    private void setText(){
        mTime.post(new Runnable() {
            @Override
            public void run() {
                String updatedTime;
                String minutesString;
                String secondsString;
                String centiString;

                //Place a 0 before single digit minute and second values
                if (mMinutes < 10 && mHours > 0) {
                    minutesString = "" + 0 + mMinutes;
                } else {
                    minutesString = "" + mMinutes;
                }
                if (mSeconds < 10) {
                    secondsString = "" + 0 + mSeconds;
                } else {
                    secondsString = "" + mSeconds;
                }
                if (mCentiSec < 10) {
                    centiString = "" + 0 + mCentiSec;
                } else {
                    centiString = "" + mCentiSec;
                }

                //Format entire string depending on value of hour and minute
                if (mHours > 0) {
                    updatedTime = mHours + ":" + minutesString + ":" + secondsString
                            + ":" + centiString;
                } else {
                    updatedTime = minutesString + ":" + secondsString + ":" + centiString;
                }
                //Set TextView text to processed string
                mTime.setText(updatedTime);

            }
        });
    } //end of setText()


    /**
     * This method makes bContinue flag false which ends the while loop in the thread created by
     * startTimer(). It then switches the View that is visible so reset button can be pressed.
     */
    void pauseTimer() {
        //toggle flag
        bContinue = false;

        //swap visible views
        primary.setVisibility(View.GONE);
        secondary.setVisibility(View.VISIBLE);
    }


    /**
     * This method resets the value of currTime and swaps the Views that are visible so user can
     * start a new stopwatch count.
     */
    void resetTimer(){
        //end the thread
        mThread.interrupt();

        //swap visible views
        primary.setVisibility(View.VISIBLE);
        secondary.setVisibility(View.GONE);

        //reset the TextView time
        String initTime = "00:00:00";
        mTime.setText(initTime);

        //update the button text
        String startString = "Start";
        mStart.setText(startString);

        //reset internal counter
        currTime = 0;
    }



} //end of class
