package com.example.k3vn19.paq.screen.alarms.screens.alarmsMain.view;

import com.example.k3vn19.paq.common.ViewBaseInterface;

/**
 * Created by k3vn19 on 1/30/2018.
 *
 * Interface for communication between AlarmsMain and DetailedAlarm. When method of AlarmsMainViewListener
 * is called then AlarmsMain will transition to the DetailedAlarmFragment of the selected AlarmEntity.
 */

public interface AlarmsMainViewInterface extends ViewBaseInterface {

    interface AlarmsMainViewListener{
        void listTap(int i);
        void deleteItem(int i);
    }
}
