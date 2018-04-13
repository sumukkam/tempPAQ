package com.example.k3vn19.paq.screen.alarms.screens.detailedAlarm.model;

import android.content.Context;

import com.example.k3vn19.paq.utils.database.AlarmEntity;
import com.example.k3vn19.paq.utils.database.DataBaseHelper;

import java.util.ArrayList;

/**
 * Created by k3vn19 on 1/30/2018.
 *
 * Purpose - This is the model for DetailedAlarm. This class will get information for the AlarmEntity
 *           that is being accessed.
 */

public class DetailedAlarmModel {

    private DataBaseHelper helper;

    public  DetailedAlarmModel(Context context){
        helper = new DataBaseHelper(context);
    }

    public void editElement(int index, AlarmEntity alarmEntity){
        helper.editElement(index, alarmEntity);
    }

    public void addElement(AlarmEntity alarmEntity){
        helper.addElement(alarmEntity);
    }

    public AlarmEntity getElement(int index){
        return helper.getElement(index);
    }

    public int getListSize(){
        return helper.getListSize();
    }


}
