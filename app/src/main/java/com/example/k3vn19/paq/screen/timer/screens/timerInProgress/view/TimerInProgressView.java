package com.example.k3vn19.paq.screen.timer.screens.timerInProgress.view;

import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.k3vn19.paq.R;

/**
 * Created by k3vn19 on 2/26/2018.
 *
 * View for TimerInProgress MVC.
 */

public class TimerInProgressView implements TimerIPViewInterface{

    private ProgressBar mProgressBar;

    private TextView mTime;

    private int hour;           //values passed in from fragment
    private int minute;
    private int seconds;
    private long initSeconds;   //in millseconds - initial amount of msec passed from fragmen
    private long counterTime;   //in milliseconds - indicates remaining milliseconds until timer goes off

    private View mRootView;
    private TimerIPViewListener mListener;

    private LayoutInflater inflater;
    private ViewGroup container;

    private Handler handler = new Handler();    //handler and drawable used for ring animation
    private Drawable drawable;

    private final int pbMax = 10000; //increase pbMax and decrease thread.sleep to get smoother animations

    /**
     * Constructor for Timer
     * @param inflater - LayoutInflater
     * @param container - ViewGroup
     */
    public TimerInProgressView(LayoutInflater inflater, ViewGroup container, TimerIPViewListener listener,
                               Drawable drawable, int hour, int minute){

        Log.d("TimerInProgressView", "Constructor================");

        mRootView = inflater.inflate(R.layout.view_timer_in_progress, container, false);

        this.inflater = inflater;
        this.container = container;
        this.mListener = listener;
        this.drawable = drawable;

        //record time
        this.hour = hour;
        this.minute = minute;
        this.seconds = 0; //Initialize to 0 since no time has been subtracted from minutes

        //convert time to milliseconds and record values
        this.initSeconds = ((hour*60) + minute)*60*1000;
        this.counterTime = initSeconds;

        initialize();
    }

    /**
     * Purpose - Initialize the button, onClickListener, TextViews and the ProgressBar. Then call
     *           runAnimation() to begin the timer animation.
     */
    private void initialize(){

        //Button initialization
        TextView IPButton = mRootView.findViewById(R.id.tempButtonIP);
        IPButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                cancelTimer();
            }
        });

        //TextView initialization
        mTime = mRootView.findViewById(R.id.TIP_tv_time);
        String updatedTime = hour + ":" + minute;
        mTime.setText(updatedTime);

        //ProgressBar initialization
        mProgressBar = mRootView.findViewById(R.id.pb_timerIP);
        mProgressBar.setProgress(0); //Primary progress is the color that changes in the animation
        mProgressBar.setSecondaryProgress(pbMax); //This is the background color that doesn't change
        mProgressBar.setMax(pbMax);
        mProgressBar.setProgressDrawable(drawable);

        runAnimation();
    } //end of initialize()

    /**
     * Purpose - This method creates a new thread to continuously update progress of ProgressBar.
     *           The Thread sleeps for 10 milliseconds and substracts 10 from counterTimer, after
     *           each subtraction the fraction of how much time is less is computed and then set as
     *           the progress of the ProgressBar.
     */
    private void runAnimation(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                //While loop runs until the time remaining reaches 0, at which point break is called.
                while (true){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Calculate the fraction of time remaining and set it as progress
                            int currProgress = (int)(((double)counterTime/(double)initSeconds) * pbMax);
                            mProgressBar.setProgress(currProgress);
                        }
                    });

                    try {
                        //Sleep the thread to mimic the behavior of a CountDownTimer
                        Thread.sleep(10); //pause thread for 10 milliseconds
                        counterTime -= 10;
                        if(counterTime < 0){
                            //end of the timer has been reached, set time textview to 0:0:0 and break
                            mTime.post(new Runnable() {
                                @Override
                                public void run() {
                                    String updatedTime = "0:0:0";
                                    mTime.setText(updatedTime);
                                }
                            });

                            break;
                        }
                        else{
                            //convert to minutes
                            long secondsLeft = counterTime / 1000;
                            int minutes = (int) (secondsLeft / 60);
                            secondsLeft -= minutes * 60;
                            seconds = (int) secondsLeft;

                            //convert to hour and assign remainder to minute
                            hour = minutes / 60;
                            minute = minutes - hour * 60; //+ 1; //add 1 to make up for truncated seconds

                            //Correct hour if minute is 60
                            if(minute == 60){
                                minute = 0;
                                hour += 1;
                            }

                            //Call post to update UI from within a thread, update hour and min TV's
                            mTime.post(new Runnable() {
                                @Override
                                public void run() {
                                    String updatedTime;
                                    String minutesString;
                                    String secondsString;

                                    //Place a 0 before single digit minute and second values
                                    if(minute < 10 && hour > 0){
                                        minutesString = "" + 0 + minute;
                                    } else{
                                        minutesString = "" + minute;
                                    }
                                    if(seconds < 10){
                                        secondsString = "" + 0 + seconds;
                                    } else{
                                        secondsString = "" + seconds;
                                    }

                                    //Format entire string depending on value of hour and minute
                                    if(hour > 0) {
                                        updatedTime = hour + ":" + minutesString + ":" + secondsString;
                                    }
                                    else if(minute == 0 & hour == 0){
                                        updatedTime = "<1 Minute";
                                    }
                                    else{
                                        updatedTime = minutesString + ":" + secondsString;
                                    }

                                    //Set TextView text to processed string
                                    mTime.setText(updatedTime);
                                }
                            });
                        }
                    }catch (InterruptedException e) {
                        //Catch any exception from sleeping the thread
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    } //end of runAnimation()

    /**
     * Purpose - Callled from onClick of cancel "button" TextView.
     */
    private void cancelTimer() {
        mListener.cancelTimer();
    }

    @Override
    public View getRootView() {
        return mRootView;
    }

} //End of class
