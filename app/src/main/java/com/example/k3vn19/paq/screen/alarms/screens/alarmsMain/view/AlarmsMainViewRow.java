package com.example.k3vn19.paq.screen.alarms.screens.alarmsMain.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.k3vn19.paq.R;
import com.example.k3vn19.paq.utils.database.AlarmEntity;

/**
 * Created by k3vn19 on 1/30/2018.
 *
 * Purpose - This is the "alarm card" that will display basic info about the alarm.
 */

public class AlarmsMainViewRow extends LinearLayout {

    private TextView time;
    private TextView ampm;
    private TextView daysOfWeek;
    public Switch alarmSetSwitch;
    //private TextView date;

    public AlarmsMainViewRow(Context context){
        super(context);
        initializeView(context);
    }

    public AlarmsMainViewRow(Context context, AttributeSet set){
        super(context, set);
        initializeView(context);
    }

    public AlarmsMainViewRow(Context context, AttributeSet set, int defStyle){
        super(context, set, defStyle);
        initializeView(context);
    }

    public void initializeView(Context context){
        View.inflate(context, R.layout.customview_alarms_list_view_row, this);

        //values to display on the card
        time = this.findViewById(R.id.alarms_list_row_time);
        ampm = this.findViewById(R.id.alarms_list_row_time_ampm);
        daysOfWeek = this.findViewById(R.id.alarms_list_row_days_of_week);
        alarmSetSwitch = this.findViewById(R.id.alarmSetToggle);

        //date = this.findViewById(R.id.alarms_list_row_date);
    }

    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);
    }

    public void setData(AlarmEntity data){

       String timeStr = data.getTimeStr();

       time.setText(timeStr.substring(0, timeStr.length()-2));
       ampm.setText(timeStr.substring(timeStr.length()-2, timeStr.length()));

       setDaysOfWeekToRepeat(data);

        if(data.isSet()) {
            alarmSetSwitch.setChecked(true);
        } else {
            alarmSetSwitch.setChecked(false);
        }
    }


    public void setDaysOfWeekToRepeat(AlarmEntity data) {

        // Set the default text to display
        daysOfWeek.setText("Alarm");

        boolean [] daysOfWeekToRepeat = data.getDaysOfWeek();
        String daysToRepeatStr = "";

        if(daysOfWeekToRepeat[1]) {
            daysToRepeatStr += "Mon, ";
        }
        if(daysOfWeekToRepeat[2]) {
            daysToRepeatStr += "Tue, ";
        }
        if(daysOfWeekToRepeat[3]) {
            daysToRepeatStr += "Wed, ";
        }
        if(daysOfWeekToRepeat[4]) {
            daysToRepeatStr += "Thu, ";
        }
        if(daysOfWeekToRepeat[5]) {
            daysToRepeatStr += "Fri, ";
        }
        if(daysOfWeekToRepeat[6]) {
            daysToRepeatStr += "Sat, ";
        }
        if(daysOfWeekToRepeat[0]) {
            daysToRepeatStr += "Sun, ";
        }
        if(daysOfWeekToRepeat[0] || daysOfWeekToRepeat[1] || daysOfWeekToRepeat[2] || daysOfWeekToRepeat[3] ||
                daysOfWeekToRepeat[4] || daysOfWeekToRepeat[5] || daysOfWeekToRepeat[6]) {
            daysOfWeek.setText(daysToRepeatStr.substring(0, daysToRepeatStr.length()-2));
        }


        if(daysOfWeekToRepeat[0] && daysOfWeekToRepeat[1] && daysOfWeekToRepeat[2] && daysOfWeekToRepeat[3] &&
                daysOfWeekToRepeat[4] && daysOfWeekToRepeat[5] && daysOfWeekToRepeat[6]) {
            daysOfWeek.setText("Everyday");
        }


        if(!daysOfWeekToRepeat[0] && daysOfWeekToRepeat[1] && daysOfWeekToRepeat[2] && daysOfWeekToRepeat[3] &&
                daysOfWeekToRepeat[4] && daysOfWeekToRepeat[5] && !daysOfWeekToRepeat[6]) {
            daysOfWeek.setText("Weekdays");
        }


        if(daysOfWeekToRepeat[0] && !daysOfWeekToRepeat[1] && !daysOfWeekToRepeat[2] && !daysOfWeekToRepeat[3] &&
                !daysOfWeekToRepeat[4] && !daysOfWeekToRepeat[5] && daysOfWeekToRepeat[6]) {
            daysOfWeek.setText("Weekends");
        }


    }
}
