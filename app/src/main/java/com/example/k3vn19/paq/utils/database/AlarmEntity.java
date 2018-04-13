package com.example.k3vn19.paq.utils.database;

import java.util.Random;

/**
 * Created by k3vn19 on 1/30/2018.
 *
 * AlarmEntity is the object that will have all the info about a single alarm.
 */

public class AlarmEntity {

    private int hour;
    private int minute;
    private boolean set;
    private boolean[] daysOfWeek;
    private int maxNumSnooze;
    private int snoozeDuration;
    private int seqLength;
    private int intensity;
    private int absIndex;
    private int key; //Key used as unique identifier for each alarm

    //Default consructor
    public AlarmEntity(){
        setDaysOfWeek(new boolean[7]);
        generateKey();
    }

    public AlarmEntity(int hour, int minute, boolean set, boolean[] daysOfWeek, int maxNumSnooze,
                       int snoozeDuration, int seqLength, int intensity){
        this.setHour(hour);
        this.setMinute(minute);
        this.setSet(set);
        this.setDaysOfWeek(daysOfWeek);
        this.setMaxNumSnooze(maxNumSnooze);
        this.setSnoozeDuration(snoozeDuration);
        this.setSeqLength(seqLength);
        this.setIntensity(intensity);


        generateKey();
    }

    /**
     * Purpose - Generates a random key and assigns it to the alarm entity.
     */
    public void generateKey(){
        int digits[] = new int[4];
        Random generator = new Random();

        for(int i = 0; i < 4; i++){
            digits[i] = generator.nextInt(10);
        }

        //format the key such that it is a 4 digit concatenated number
        key = digits[0]*1000 + digits[1]*100 + digits[2]*10 + digits[3];
    }

    //Getters and Setters
    public String getTimeStr(){
        String timeStr = "";

        int hour = getHour();
        String amPm = "";


        if(hour > 12) {
            hour = hour - 12;
            amPm = "PM";
        }
        else if(hour == 12) {
            amPm = "PM";
        }
        else if(hour == 0) {
            hour = 12;
            amPm = "AM";
        }
        else {
            amPm = "AM";
        }

        timeStr = hour + ":" + getMinute() + " " + amPm;

        if(getMinute() < 10) {
            timeStr = hour + ":0" + getMinute() + " " + amPm;
        }

        return timeStr;
    }

    public int getHour() {return hour;}
    public void setHour(int hour) { this.hour = hour;}

    public int getMinute() {return minute;}
    public void setMinute(int minute) {this.minute = minute;}

    public boolean isSet() {return set;}
    public void setSet(boolean set) {this.set = set;}

    public int getMaxNumSnooze() {return maxNumSnooze;}
    public void setMaxNumSnooze(int maxNumSnooze) {this.maxNumSnooze = maxNumSnooze;}

    public int getSnoozeDuration() {return snoozeDuration;}
    public void setSnoozeDuration(int snoozeDuration) {this.snoozeDuration = snoozeDuration;}

    public int getSeqLength() {return seqLength;}
    public void setSeqLength(int seqLength) {this.seqLength = seqLength;}

    public int getIntensity() {return intensity;}
    public void setIntensity(int intensity) {this.intensity = intensity;}

    public boolean[] getDaysOfWeek() {return daysOfWeek;}
    public void setDaysOfWeek(boolean[] daysOfWeek) {this.daysOfWeek = daysOfWeek;}

    public int getAbsIndex() {return absIndex; }
    public void setAbsIndex(int index) {this.absIndex = index;}

    public int getKey() {
        return key;
    }
    public void setKey(int key){this.key = key;}
}
