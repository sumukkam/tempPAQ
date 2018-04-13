package com.example.k3vn19.paq.screen.alarms.screens.detailedAlarm.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.example.k3vn19.paq.R;
import com.example.k3vn19.paq.screen.alarms.screens.detailedAlarm.model.DetailedAlarmModel;
import com.example.k3vn19.paq.screen.alarms.screens.detailedAlarm.view.DetailedAlarmInterface;
import com.example.k3vn19.paq.screen.alarms.screens.detailedAlarm.view.DetailedAlarmView;
import com.example.k3vn19.paq.utils.database.AlarmEntity;

/**
 * Created by k3vn19 on 2/5/2018.
 *
 * Purpose - Controller for DetailedAlarm. This package is for viewing specifics about alarms i.e.
 *           sequence, snooze, and intensity settings. The user will be able to edit alarm parameters
 *           and set alarms.
 */

public class DetailedAlarmActivity extends AppCompatActivity implements DetailedAlarmInterface.DetailedAlarmListener {

    private DetailedAlarmModel mModel;
    private DetailedAlarmView mView;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.primaryDarkGrey));

        Intent intent = getIntent();
        if(intent.getExtras() != null) {
            this.index = intent.getExtras().getInt("index");
        }

        mModel = new DetailedAlarmModel(this);

        if(index != -1) {
            mView = new DetailedAlarmView(LayoutInflater.from(this), null, mModel.getElement(index), index, this);
        } else {
            mView = new DetailedAlarmView(LayoutInflater.from(this), null, null, index, this);
        }
        mView.setListener(this);

        setContentView(mView.getRootView());
    }

    @Override
    public void cancelAlarm(){
        finish();
        overridePendingTransition(R.anim.scale_in, R.anim.scale_out);
    }

    @Override
    public void editAlarm(int index, AlarmEntity alarmEntity) {
        //make call to mModel to update database
        mModel.editElement(index, alarmEntity);
        finish();
        overridePendingTransition(R.anim.scale_in, R.anim.scale_out);

    }

    @Override
    public void addAlarm(AlarmEntity alarmEntity) {

        //make call to mModel to add AlarmEntity database
        if(!checkIfAlarmExists(alarmEntity)) {
            mModel.addElement(alarmEntity);
            finish();
            overridePendingTransition(R.anim.scale_in, R.anim.scale_out);

        }
        else{
            //Since alarm wasn't added, Toast to the user to let them know alarm exists
            Toast.makeText(this, "Alarm with given parameters already exists!",
                    Toast.LENGTH_SHORT ).show();
        }


    }

    /**
     * Purpose - Check if given alarm has already been made. Check is based on hour, min and days.
     * @param alarmEntity - Alarm to compare to
     * @return False if alarm doesn't exist, true if it matches another alarm
     */
    public boolean checkIfAlarmExists(AlarmEntity alarmEntity){

        //get size and create an array of booleans, each boolean represents whether or not alarm at
        //that index number in LinkedList of AlarmEntities is the same or not. For alarm to be new
        //all indexes must be false. If any index is true then alarm already exists.
        int size = mModel.getListSize();
        boolean exists[] = new boolean[size];


        //First assume all alarms are the same as this one
        for(int i = 0; i < size; i++){
            exists[i] = true;
        }

        //Iterate through list, if a parameter doesn't match up then make that index of exists[] false
        // since it is known that they cannot be the same
        for(int i = 0; i < size; i++){
            AlarmEntity currAlarm = mModel.getElement(i);
            if(currAlarm.getHour() != alarmEntity.getHour()){
                exists[i] = false; //since hour differs, set to false since alarms differ
            }
            else if(currAlarm.getMinute() != alarmEntity.getMinute()){
                exists[i] =  false; //since minutes differs, set to false since alarms differ
            }
            else{
                for(int j = 0; j < 7; j++){
                    if(currAlarm.getDaysOfWeek()[j] != alarmEntity.getDaysOfWeek()[j]){
                        exists[i] = false; //since days of week differ, set to false since alarms differ
                    }
                }
            }
        }

        //Now that all alarms have been checked, check if any index is still true. If there's an index
        //that is still true it means it didn't pass any of the checks above. So return true since alarm
        //already exists
        for(int i = 0; i < size; i++){
            if(exists[i]){
                return true;
            }
        }

        //Return false if every index of exists[] is false
        return false;
    }

} //end of class
