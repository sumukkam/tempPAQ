package com.example.k3vn19.paq.screen.alarms.screens.detailedAlarm.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import com.example.k3vn19.paq.R;
import com.example.k3vn19.paq.utils.database.AlarmEntity;

//import org.w3c.dom.Text;

import java.util.Calendar;

/**
 * Created by k3vn19 on 1/30/2018.
 *
 * Purpose - View for DetailedAlarm, handles user input for DetailedAlarm page.
 */

public class DetailedAlarmView implements DetailedAlarmInterface{

    private SeekBar sb_numSnooze;
    private SeekBar sb_snoozeLength;
    private SeekBar sb_intensity;
    private SeekBar sb_seqLength;

    private TextView tv_numSnooze;
    private TextView tv_snoozeLength;
    private TextView tv_intensity;
    private TextView tv_seqLength;

    private TimePicker timePicker;

    private ToggleButton sundayToggle;
    private ToggleButton mondayToggle;
    private ToggleButton tuesdayToggle;
    private ToggleButton wednessdayToggle;
    private ToggleButton thursdayToggle;
    private ToggleButton fridayToggle;
    private ToggleButton saturdayToggle;

    private View durationLayout;        //layout that holds snooze duration. Hidden is number of snooze is 0
    private boolean isDisabled = false; //flags whether snooze duration is able to getProgress from

    private View mRootView;
    private DetailedAlarmListener listener;

    private boolean setAlarmToggle;

    private boolean[] daysOfWeek = new boolean[7];

    private int alarmIndex;
    private AlarmEntity alarmEntity;

    private LayoutInflater inflater;
    private ViewGroup container;
    private Context context;

    public DetailedAlarmView(LayoutInflater inflater, ViewGroup container, AlarmEntity alarmEntity,
                             int index, Context context){
        Log.d("DetailedAlarmsView", "consructor===============");

        mRootView = inflater.inflate(R.layout.view_detailed_alarm, container, false);
        this.alarmEntity = alarmEntity;

        this.alarmIndex = index;

        this.inflater = inflater;
        this.container = container;
        this.context = context;

        initialize();

        if(alarmEntity != null) {
            initializeExisting(alarmEntity);
        }
    }


    /**
     * Purpose - Initialize the seekbars, timepicker, textviews and button.
     */
    private void initialize(){
        TextView cancelAlarm = mRootView.findViewById(R.id.cancelAlarm);
        TextView saveAlarm = mRootView.findViewById(R.id.saveAlarm);
        cancelAlarm.setOnClickListener(new View.OnClickListener(){
           public void onClick(View v){
               onCancelAlarm();
           }
        });

        saveAlarm.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                onSaveAlarm();
            }
        });

        setAlarmToggle = true;

        Log.d("DetailedAlarmsView", "initialize()===============");

        //seekbars
        sb_numSnooze = mRootView.findViewById(R.id.sb_numSnooze);
        int MAXNUMSNOOZE = 3;
        sb_numSnooze.setMax(MAXNUMSNOOZE);

        sb_snoozeLength = mRootView.findViewById(R.id.sb_snoozeDuration);
        int MAXSNOOZELENGTH = 10;
        sb_snoozeLength.setMax(MAXSNOOZELENGTH);

        sb_intensity = mRootView.findViewById(R.id.sb_intensity);
        int MAXINTENSITY = 5;
        sb_intensity.setMax(MAXINTENSITY);

        sb_seqLength = mRootView.findViewById(R.id.sb_seqLength);
        int MAXSEQLENGTH = 7;
        sb_seqLength.setMax(MAXSEQLENGTH);

        //Textviews
        tv_intensity = mRootView.findViewById(R.id.tv_intensity);
        tv_numSnooze = mRootView.findViewById(R.id.tv_numSnooze);
        tv_seqLength = mRootView.findViewById(R.id.tv_seqLength);
        tv_snoozeLength = mRootView.findViewById(R.id.tv_snoozeDuration);

        //timepicker
        timePicker = mRootView.findViewById(R.id.detailed_timePicker);
        setDividerColor(timePicker, context.getResources().getColor(R.color.primaryYellow));

        // Days of week toggle buttons
        sundayToggle = mRootView.findViewById(R.id.sundayToggle);
        mondayToggle =  mRootView.findViewById(R.id.mondayToggle);
        tuesdayToggle = mRootView.findViewById(R.id.tuesdayToggle);
        wednessdayToggle = mRootView.findViewById(R.id.wednessdayToggle);
        thursdayToggle = mRootView.findViewById(R.id.thursdayToggle);
        fridayToggle = mRootView.findViewById(R.id.fridayToggle);
        saturdayToggle = mRootView.findViewById(R.id.saturdayToggle);

        durationLayout = mRootView.findViewById(R.id.detailed_layout_duration);

        //disable the duration seekbar and layout since by default snooze amount is 0
        disableDuration(true);

        // Set on click listeners to them
        initializeToggleButtons();

        //Initialize textviews to display 0.
        setInitialSBText();

        intializeSeekerListeners();
    }


    /**
     * Purpose - If an existing alarm was clicked, we need to set the seekbars and alarm time to
     *           those existing values
     */
    private void initializeExisting(AlarmEntity alarmEntity) {

        TextView detailedAlarmTitle =  mRootView.findViewById(R.id.detailed_alarm_title);
        String setEditString = "Edit Alarm";
        detailedAlarmTitle.setText(setEditString);

        timePicker.setCurrentHour(alarmEntity.getHour());
        timePicker.setCurrentMinute(alarmEntity.getMinute());

        this.setAlarmToggle = alarmEntity.isSet();

        sb_numSnooze.setProgress(alarmEntity.getMaxNumSnooze());
        if(alarmEntity.getMaxNumSnooze() == 0){
            disableDuration(true);
        }
        else{
            sb_snoozeLength.setProgress(alarmEntity.getSnoozeDuration());
        }
        sb_intensity.setProgress(alarmEntity.getIntensity());
        sb_seqLength.setProgress(alarmEntity.getSeqLength());

        this.daysOfWeek = alarmEntity.getDaysOfWeek();

        if(daysOfWeek[0]) {
            sundayToggle.toggle();
        }
        if(daysOfWeek[1]) {
            mondayToggle.toggle();
        }
        if(daysOfWeek[2]) {
            tuesdayToggle.toggle();
        }
        if(daysOfWeek[3]) {
            wednessdayToggle.toggle();
        }
        if(daysOfWeek[4]) {
            thursdayToggle.toggle();
        }
        if(daysOfWeek[5]) {
            fridayToggle.toggle();
        }
        if(daysOfWeek[6]) {
            saturdayToggle.toggle();
        }
    } //end of method

    /**
     * Purpose - Change the color of the divider of the number picker
     * @param picker - NumberPicker to change color of
     * @param color - Color to change the divider to
     */
    private void setDividerColor(TimePicker picker, int color) {

        java.lang.reflect.Field[] pickerFields = TimePicker.class.getDeclaredFields();
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
     * Purpose - Set the text of the TextViews above the SeekBars initially to min values.
     */
    private void initializeToggleButtons() {
        sundayToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    sundayToggle.setTextColor(ContextCompat.getColor(mRootView.getContext(), R.color.detailedAlarmDaysOfWeekToggleOnTextColor));
                    daysOfWeek[0] = true;
                }
                else {
                    sundayToggle.setTextColor(ContextCompat.getColor(mRootView.getContext(), R.color.detailedAlarmDaysOfWeekToggleOffTextColor));
                    daysOfWeek[0] = false;
                }
            }
        });

        mondayToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    mondayToggle.setTextColor(ContextCompat.getColor(mRootView.getContext(), R.color.detailedAlarmDaysOfWeekToggleOnTextColor));
                    daysOfWeek[1] = true;
                }
                else {
                    mondayToggle.setTextColor(ContextCompat.getColor(mRootView.getContext(), R.color.detailedAlarmDaysOfWeekToggleOffTextColor));
                    daysOfWeek[1] = false;
                }
            }
        });

        tuesdayToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    tuesdayToggle.setTextColor(ContextCompat.getColor(mRootView.getContext(), R.color.detailedAlarmDaysOfWeekToggleOnTextColor));
                    daysOfWeek[2] = true;
                }
                else {
                    tuesdayToggle.setTextColor(ContextCompat.getColor(mRootView.getContext(), R.color.detailedAlarmDaysOfWeekToggleOffTextColor));
                    daysOfWeek[2] = false;
                }
            }
        });

        wednessdayToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    wednessdayToggle.setTextColor(ContextCompat.getColor(mRootView.getContext(), R.color.detailedAlarmDaysOfWeekToggleOnTextColor));
                    daysOfWeek[3] = true;
                }
                else {
                    wednessdayToggle.setTextColor(ContextCompat.getColor(mRootView.getContext(), R.color.detailedAlarmDaysOfWeekToggleOffTextColor));
                    daysOfWeek[3] = false;
                }
            }
        });

        thursdayToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    thursdayToggle.setTextColor(ContextCompat.getColor(mRootView.getContext(), R.color.detailedAlarmDaysOfWeekToggleOnTextColor));
                    daysOfWeek[4] = true;
                }
                else {
                    thursdayToggle.setTextColor(ContextCompat.getColor(mRootView.getContext(), R.color.detailedAlarmDaysOfWeekToggleOffTextColor));
                    daysOfWeek[4] = false;
                }
            }
        });

        fridayToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    fridayToggle.setTextColor(ContextCompat.getColor(mRootView.getContext(), R.color.detailedAlarmDaysOfWeekToggleOnTextColor));
                    daysOfWeek[5] = true;
                }
                else {
                    fridayToggle.setTextColor(ContextCompat.getColor(mRootView.getContext(), R.color.detailedAlarmDaysOfWeekToggleOffTextColor));
                    daysOfWeek[5] = false;
                }
            }
        });

        saturdayToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    saturdayToggle.setTextColor(ContextCompat.getColor(mRootView.getContext(), R.color.detailedAlarmDaysOfWeekToggleOnTextColor));
                    daysOfWeek[6] = true;
                }
                else {
                    saturdayToggle.setTextColor(ContextCompat.getColor(mRootView.getContext(), R.color.detailedAlarmDaysOfWeekToggleOffTextColor));
                    daysOfWeek[6] = false;
                }
            }
        });

    }


    /**
     * Purpose - Set the text of the TextViews above the SeekBars initially to min values.
     */
    private void setInitialSBText(){
        int MINSEQLENGTH = 0;
        String initSeqLength = " " + MINSEQLENGTH;
        int MINSNOOZELENGTH = 1;
        String initSnoozeLength = " " + MINSNOOZELENGTH;
        int MINNUMSNOOZE = 0;
        String initNumSnooze = " " + MINNUMSNOOZE;
        int MININTENSITY = 1;
        String initIntensity = " " + MININTENSITY;

        tv_seqLength.setText(initSeqLength);
        tv_snoozeLength.setText(initSnoozeLength);
        tv_numSnooze.setText(initNumSnooze);
        tv_intensity.setText(initIntensity);
    }

    /**
     * Purpose - set onSeekBarChangeListeners on all seekers to track values
     */
    private void intializeSeekerListeners() {

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
                String toShow = " " + (i+1); //add 1 to increase "min" value artificially
                tv_intensity.setText(toShow);
            }

            @Override //not used
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override //not used
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        sb_snoozeLength.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                String toShow = " " + (i+1);
                tv_snoozeLength.setText(toShow);
            }

            @Override //not used
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override //not used
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        sb_numSnooze.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(i == 0){
                    disableDuration(true);
                    String toShow = " " + 0;
                    tv_numSnooze.setText(toShow);
                } else{
                    disableDuration(false);
                    String toShow = " " + i;
                    tv_numSnooze.setText(toShow);
                }

            }

            @Override //not used
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override //not used
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

    }

    /**
     * When the number of snooze is equal to 0, hide the snooze duration layout. Otherwise make it
     * visible.
     */
    private void disableDuration(boolean disable){
        if(disable){
            Log.d("DetailedAlarmView","disableDuration = true====================");
            durationLayout.setVisibility(View.GONE);
            sb_snoozeLength.setVisibility(View.GONE);
            isDisabled = true;
        }
        else{
            Log.d("DetailedAlarmView","disableDuration = false====================");
            durationLayout.setVisibility(View.VISIBLE);
            sb_snoozeLength.setVisibility(View.VISIBLE);
            isDisabled = false;
        }
    }

    @Override
    public void setListener(DetailedAlarmListener listener) {
        this.listener = listener;
    }

    @Override
    public View getRootView() {
        return mRootView;
    }

    private void onCancelAlarm(){
        listener.cancelAlarm();
    }

    /**
     * Purpose - When Save button is clicked this method is called to create a new alarm with parameters
     *           input in the view. This alarm is then passed to the listener for the controller to use.
     */
    private void onSaveAlarm() {
        //create a new alarm to save
        AlarmEntity alarmToSave;
        if(!isDisabled) {
            alarmToSave = new AlarmEntity(timePicker.getCurrentHour(), timePicker.getCurrentMinute(),
                    setAlarmToggle, daysOfWeek, sb_numSnooze.getProgress(),
                    sb_snoozeLength.getProgress() + 1, sb_seqLength.getProgress(),
                    sb_intensity.getProgress() + 1);
            //Intensity and snooze duration are increased by 1 to reflect that minimum is 1 instead of 0, therefore every other number is also pushed
            //up by 1
        }
        else{
            alarmToSave = new AlarmEntity(timePicker.getCurrentHour(), timePicker.getCurrentMinute(),
                    setAlarmToggle, daysOfWeek, sb_numSnooze.getProgress(),0, sb_seqLength.getProgress(),
                    sb_intensity.getProgress() + 1);
            //Intensity is increased by 1 to reflect that minimum is 1 instead of 0, therefore every other number is also pushed
            //up by 1

            //In this case since isDisabled is true, can't get progress of snooze duration is default to 0
        }

        if(alarmEntity == null){
            listener.addAlarm(alarmToSave);
        }
        else{
            listener.editAlarm(alarmIndex, alarmToSave);
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
} //end of class

