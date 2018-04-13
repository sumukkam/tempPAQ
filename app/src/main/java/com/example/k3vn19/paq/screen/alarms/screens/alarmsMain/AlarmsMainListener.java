package com.example.k3vn19.paq.screen.alarms.screens.alarmsMain;

import com.example.k3vn19.paq.utils.database.AlarmEntity;

import java.util.LinkedList;

/**
 * Created by k3vn19 on 1/30/2018.
 *
 * Purpose- This interface is for method that transitions from the list of alarms to a specific alarm.
 */

public interface AlarmsMainListener {

    void moveToDetailFragment(int i);
}
