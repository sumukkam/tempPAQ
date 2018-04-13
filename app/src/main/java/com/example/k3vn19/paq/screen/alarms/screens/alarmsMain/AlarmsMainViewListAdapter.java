package com.example.k3vn19.paq.screen.alarms.screens.alarmsMain;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.k3vn19.paq.screen.alarms.screens.alarmsMain.view.AlarmsMainViewRow;
import com.example.k3vn19.paq.utils.database.AlarmEntity;
import com.example.k3vn19.paq.utils.database.DataBaseHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;

/**
 * Created by k3vn19 on 1/30/2018.
 *
 * Purpose - Adapter for AlarmsMain.
 */

public class AlarmsMainViewListAdapter extends BaseAdapter {

    private Context context;
    private LinkedList<AlarmEntity> data = new LinkedList<>();

    public AlarmsMainViewListAdapter(Context context){this.context = context;}

    public void setData(LinkedList<AlarmEntity> data){
        this.data = data;
        this.notifyDataSetChanged();
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();

        DataBaseHelper dbHelper = new DataBaseHelper();
        data = dbHelper.getList();
    }

    @Override
    public int getCount(){ return data.size();}

    @Override
    public Object getItem(int i){return data.get(i);}

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(final int i, View view, ViewGroup group){
        if(view == null){
            DataBaseHelper dbHelper = new DataBaseHelper();
            data = dbHelper.getList();
            AlarmsMainViewRow row = new AlarmsMainViewRow(context);
            row.setData(data.get(i));
            Log.d("Toggle Button Changed", "Absolute Index In List: " + data.get(i).getAbsIndex() + " toggle was changed to " + data.get(i).isSet());

            final int index = i;
            row.alarmSetSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    DataBaseHelper dbHelper = new DataBaseHelper();
                    AlarmEntity newAlarm = data.get(index);
                    newAlarm.setSet(b);


                    //If the toggle is set to on Toast to the user how much time is left until alarm goes off
                    if(b){
                        Toast.makeText(context, calculateTimeLeft(index), Toast.LENGTH_SHORT).show();
                    }

                    dbHelper.editElement(data.get(index).getAbsIndex(), newAlarm);
                    Log.d("Toggle Button Changed", "Absolute Index In List: " + data.get(index).getAbsIndex()
                            + " toggle was changed to " + data.get(index).isSet());
                }
            });

            return row;
        }
        else{
            ((AlarmsMainViewRow)view).setData(data.get(i));
            return view;
        }
    }


    /**
     * This method calculates how much time is left until the alarm will go off and formats that
     * into a string which is returned and Toasted
     * @param index - the idndex of the alarm that is to be calculated
     * @return - Formatted string that says how many days, hours and minutes are left until alarm
     *           goes off
     */
    private String calculateTimeLeft(int index){

        //Get AlarmEntity hour and minute
        AlarmEntity alarm = data.get(index);
        float alarmHour = alarm.getHour();
        float alarmMinute = alarm.getMinute();

        //Get current time hour and minute
        Calendar currTime = Calendar.getInstance();
        float currentHour = (float) currTime.get(Calendar.HOUR_OF_DAY);
        float currentMinute = (float) currTime.get(Calendar.MINUTE);
        int currentDay = currTime.get(Calendar.DAY_OF_WEEK);
        //int indexInArray = currentDay - 1; // index for days of week in calendar start at 1,
                                           // so sub 1 to get index to start at 0 like in an array

        //If this is true, the alarm is on the next day so add 24
        if(currentHour > alarmHour){
            alarmHour += 24;
        }

        currentHour += currentMinute/60;
        alarmHour += alarmMinute/60;

        float timeDifference = alarmHour - currentHour;

        //truncate the decimal
        int hoursLeft = (int) timeDifference;
        //compute minutes left
        int minutesLeft = (int) ((timeDifference - hoursLeft) * 60);


        //===========================COMPUTING DAYS LEFT==================================


        boolean days[] = alarm.getDaysOfWeek();
        boolean nextDayFound = false;
        int currIndex = currentDay - 1; // index for days of week in calendar start at 1,
                                        // so sub 1 to get index to start at 0 like in an array
        int daysUntil = 0;

        //check if any days are toggled, if they aren't then the next day is "tomorrow"
        boolean toggleExists = false;
        for(int i = 0; i < 7; i++){
            if(days[i])
                toggleExists = true;
        }


        // if on same day, daysUntil is zero
        if(toggleExists && days[currIndex] && (currentHour+(currentMinute/60) < alarmHour+(alarmMinute/60))){
            nextDayFound = true;
            daysUntil = 0;
        }
        // if next day but within 24 hours
        else if(toggleExists && days[currIndex + 1] && (currentHour+(currentMinute/60) > alarmHour+(alarmMinute/60))){
            nextDayFound = true;
            daysUntil = 0;
        }
        // more than 24 hours just redirect to while loop
        else{
            currIndex++;
            daysUntil++;

            // reset to beginning of week if past sunday
            if(currIndex > 6)
                currIndex = 0;
        }

        // loop through until the next day is found
        while(!nextDayFound && toggleExists){
            // different day
            if(days[currIndex]){
                break;
            }
            else{
                currIndex++;
                daysUntil++;
            }

            // reset to beginning of week if past sunday
            if(currIndex > 6)
                currIndex = 0;
        }

        // =====================CORRECT ANY NEGATIVE VALUES===========================
        if(minutesLeft < 0) {
            minutesLeft += 60;
            hoursLeft--;
        }
        if(hoursLeft < 0){
            hoursLeft = 23;
            daysUntil--;
        }


        // ===========================FORMAT THE STRING=================================

        if(daysUntil == 0 && hoursLeft > 0 && minutesLeft > 0){
            return "Alarm set for " + hoursLeft + " hours and " + minutesLeft + " minutes from now.";
        }
        else if (daysUntil == 0 && hoursLeft == 0){
            return "Alarm set for " + minutesLeft + " minutes from now.";
        }
        else if (daysUntil == 0 && minutesLeft == 0) {
            return "Alarm set for " + hoursLeft + " hours from now.";
        }
        else if(hoursLeft == 0 && minutesLeft > 0){
            return "Alarm set for " + daysUntil + " days and " + minutesLeft + " minutes from now.";
        }
        else if(minutesLeft == 0 && hoursLeft > 0){
            return "Alarm set for " + daysUntil + " days and " + hoursLeft + " hours from now.";
        }
        else{
            return "Alarm set for " + daysUntil + " days, " + hoursLeft + " hours and " + minutesLeft + " minutes from now.";
        }

    }


}
