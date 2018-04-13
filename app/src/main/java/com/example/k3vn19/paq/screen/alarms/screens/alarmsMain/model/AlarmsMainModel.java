package com.example.k3vn19.paq.screen.alarms.screens.alarmsMain.model;

import com.example.k3vn19.paq.utils.database.AlarmEntity;
import com.example.k3vn19.paq.utils.database.DataBaseHelper;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by k3vn19 on 1/30/2018.
 *
 * Purpose - Model for AlarmsMain. Currently gets dumby value,
 * TODO - should retrieve info from database.
 */

public class AlarmsMainModel {

    private LinkedList<AlarmEntity> alarmsEntityList;

    public AlarmsMainModel(LinkedList<AlarmEntity> list){
        this.alarmsEntityList = list;
    }

    public LinkedList<AlarmEntity> getAlarmsEntityList(){
        return alarmsEntityList;
    }

    public void deleteAlarm(int index, DataBaseHelper helper){
        alarmsEntityList.remove(index);
        helper.deleteElement(index);
    }
}
