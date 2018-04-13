package com.example.k3vn19.paq.screen.timer.screens.timerMain.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.k3vn19.paq.R;

/**
 * Created by k3vn19 on 2/26/2018.
 *
 * View for Timer MVC.
 */

public class TimerMainView implements TimerMainViewInterface, NumberPicker.OnValueChangeListener{

    private SeekBar sb_intensity;
    private SeekBar sb_seqLength;

    private TextView tv_intensity;
    private TextView tv_seqLength;

    private NumberPicker np_hour;
    private NumberPicker np_minute;

    private TextView bt_start;

    private View mRootView;
    private TimerMainViewListener mListener;

    private LayoutInflater inflater;
    private ViewGroup container;
    private Context context;


    /**
     * Constructor for Timer
     * @param inflater - LayoutInflater
     * @param container - ViewGroup
     */
    public TimerMainView(LayoutInflater inflater, ViewGroup container, TimerMainViewListener listener,
                         Context context){

        Log.d("TimerMainView", "Constructor================");

        mRootView = inflater.inflate(R.layout.view_timer, container, false);

        this.inflater = inflater;
        this.container = container;
        this.mListener = listener;
        this.context = context;

        initialize();
    }

    /**
     * Purpose - method initializs the NumberPickers, Seekbars, and TextViews
     */
    private void initialize(){

        //seekbars
        sb_intensity = mRootView.findViewById(R.id.sb_timer_intensity);
        sb_seqLength = mRootView.findViewById(R.id.sb_timer_seqLength);

        //textviews
        tv_intensity = mRootView.findViewById(R.id.tv_timer_intensity);
        tv_seqLength = mRootView.findViewById(R.id.tv_timer_seqLength);

        //number pickers
        np_hour = mRootView.findViewById(R.id.np_timer_hour);
        np_minute = mRootView.findViewById(R.id.np_timer_minute);

        //set number pickers max and min values
        np_hour.setMaxValue(12);
        np_hour.setMinValue(0);
        np_minute.setMinValue(0);
        np_minute.setMaxValue(59);
        np_minute.setValue(10);

        setDividerColor(np_hour, context.getResources().getColor(R.color.primaryYellow));
        setDividerColor(np_minute, context.getResources().getColor(R.color.primaryYellow));

        np_minute.setOnValueChangedListener(this);
        np_hour.setOnValueChangedListener(this);

        //button
        bt_start = mRootView.findViewById(R.id.bt_timer_start);
        bt_start.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                setTimer();
            }
        });

        //set seekbar max values
        sb_intensity = mRootView.findViewById(R.id.sb_timer_intensity);
        int MAXINTENSITY = 5;
        sb_intensity.setMax(MAXINTENSITY);

        sb_seqLength = mRootView.findViewById(R.id.sb_timer_seqLength);
        int MAXSEQLENGTH = 7;
        sb_seqLength.setMax(MAXSEQLENGTH);

        tv_intensity = mRootView.findViewById(R.id.tv_timer_intensity);
        tv_seqLength = mRootView.findViewById(R.id.tv_timer_seqLength);

        //Initialize textviews to display 0.
        setInitialSBText();

        intializeSeekerListeners();
    }

    /**
     * Purpose - Set the text of the TextViews above the SeekBars initially to min values.
     */
    private void setInitialSBText(){
        int MINSEQLENGTH = 0;
        String initSeqLength = " " + MINSEQLENGTH;
        int MININTENSITY = 1;
        String initIntensity = " " + MININTENSITY;

        tv_seqLength.setText(initSeqLength);
        tv_intensity.setText(initIntensity);
    }

    /**
     * Purpose - set onSeekBarChangeListeners on all seekers to track values
     */
    private void intializeSeekerListeners(){

        sb_seqLength.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                String toShow = " " + i;
                Log.d("DetailedAlaramView", "seekBarSeqLength " + i + " ===========");
                tv_seqLength.setText(toShow);
            }

            @Override //not used
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override //not used
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        sb_intensity.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                String toShow = " " + i;
                tv_intensity.setText(toShow);
            }

            @Override //not used
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override //not used
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    } //end of intializeSeekerListeners()

    @Override
    public View getRootView() {
        return mRootView;
    }


    /**
     * Purpose - Called from onClick of setTimer button.
     */
    private void setTimer(){
        mListener.setTimer();
    }

    /**
     * Purpose - This method will change the color and setEnabled based on if Timer has a valid time.
     *           If both minute and hour are 0 the button will be disabled.
     * @param picker -
     * @param oldVal -
     * @param newVal -
     */
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        if(np_minute.getValue() == 0 && np_hour.getValue() == 0){
            bt_start.setEnabled(false);
            bt_start.setTextColor(context.getResources().getColor(R.color.secondaryTextColor));
        } else{
            bt_start.setEnabled(true);
            bt_start.setTextColor(context.getResources().getColor(R.color.primaryTextColor));
        }
    }

    /**
     * Purpose - When onResume() is called in the controller, numberpicker values are reset to 0.
     */
    public void resetNumberPicker(){
        np_hour.setValue(0);
        np_minute.setValue(10);
    }

    /**
     * Purpose - Change the color of the divider of the number picker
     * @param picker - NumberPicker to change color of
     * @param color - Color to change the divider to
     */
    private void setDividerColor(NumberPicker picker, int color) {

        java.lang.reflect.Field[] pickerFields = NumberPicker.class.getDeclaredFields();
        for (java.lang.reflect.Field pf : pickerFields) {
            if (pf.getName().equals("mSelectionDivider")) {
                pf.setAccessible(true);
                try {
                    ColorDrawable colorDrawable = new ColorDrawable(color);
                    pf.set(picker, colorDrawable);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
                catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }


    /**
     * Getter for LayoutInflater
     * @return inflater
     */
    public LayoutInflater getInflater() {
        return inflater;
    }

    /**
     * Getter for ViewGroup
     * @return container, the view's ViewGroup
     */
    public ViewGroup getContainer() {
        return container;
    }

    //Getters for creating AlarmEntity for Timer

    /**
     * Purpose - Get the hour value
     * @return - hour
     */
    public int getHour() {
        return np_hour.getValue();
    }

    /**
     * Purpose - Get the minute value
     * @return - minute
     */
    public int getMinute() {
        return np_minute.getValue();
    }

    /**
     * Purpose - Get the intensity value
     * @return - intensity
     */
    public int getIntensity(){
        return sb_intensity.getProgress();
    }

    /**
     * Purpose - Get the sequence length value
     * @return - Sequence Length
     */
    public int getSeqLength(){
        return sb_seqLength.getProgress();
    }
} //end of class
