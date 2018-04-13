package com.example.k3vn19.paq.utils.database;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by k3vn19 on 2/5/2018.
 *
 * This class handles loading into and out of the phone's local database.
 */

public class DataBaseHelper {

    static private LinkedList<AlarmEntity> mList;
    static private Context mContext;

    public DataBaseHelper() {
    }

    public DataBaseHelper(Context context) {
        mList = new LinkedList<>();
        this.mContext = context;
    }

    private void saveData() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("AlarmsDataBase", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();

        // The name of the arraylist
        String json = gson.toJson(mList);

        editor.putString("AlarmsList", json);
        editor.apply();
    }


    /**
     * Purpose - This method updates the member variable LinkedList by loading from the GSON. This
     *           method should be called in the first line of any method which adds, deletes, or
     *           updates the local data base.
     */
    private void loadData() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("AlarmsDataBase", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("AlarmsList", null);

        // The type of the arraylist objects
        Type type = new TypeToken<LinkedList<AlarmEntity>>() {
        }.getType();

        // The name of the arraylist
        mList = gson.fromJson(json, type);

        if (mList == null) {
            mList = new LinkedList<>();
        }
    }


    /**
     * Purpose - Getter for LinkedList member variable
     * @return - LinkedList
     */
    public LinkedList<AlarmEntity> getList() {
        loadData();
        return this.mList;
    }

    /**
     * Purpose - Get the AlarmEntity in the LikedList at a given index
     * @param index - The index at which to retrieve the AlarmEntity
     * @return The AlarmEntity at given index
     */
    public AlarmEntity getElement(int index) {
        loadData();
        return mList.get(index);
    }

    /**
     * Setter for List
     * @param list The new LinkedList to set.
     */
    public void setList(LinkedList<AlarmEntity> list) {
        this.mList = list;
        saveData();
    }

    /**
     * Purpose - This method saves the parameter AlarmEntity to the local database.
     * @param alarm - The AlarmEntity to be saved to the device.
     */
    public void addElement(AlarmEntity alarm) {
        loadData();
        alarm.setAbsIndex(mList.size());

        //Make sure alarms key is unique, if it isn't generate a new one until it is
        while(!hasUniqueKey(alarm)){
            alarm.generateKey();
        }

        mList.add(alarm);
        updateIndexes();
        saveData();

        Log.d("DataBaseHelper", "Alarm added with key: "+alarm.getKey());
    }

    /**
     * Purpose - Make sure the Key of the alarm is unique.
     * @return - true if the key isn't already in the data structure, false if not
     */
    private Boolean hasUniqueKey(AlarmEntity alarm){
        loadData();
        for(int i = 0; i < mList.size(); i++){
            if(alarm.getKey() == mList.get(i).getKey()){
                return false;
            }
        }

        return true;
    }

    /**
     * Purpose - This method overwrites the alarm info with an updated version of the alarm into its
     *           existing index.
     * @param index - Where in the datastructure the old version of the AlarmEntity is located
     * @param alarm - The new AlarmEntity to save with updated parameters
     */
    public void editElement(int index, AlarmEntity alarm) {
        loadData();
        alarm.setAbsIndex(index);
        mList.set(index, alarm);
        updateIndexes();
        saveData();
    }

    /**
     * Purpose - Delete the AlarmEntity at a given index in the datastructure and save that change
     *           to the device's database.
     * @param index - The index of the AlarmEntity that is to be deleted
     */
    public void deleteElement(int index) {
        loadData();
        mList.remove(index);
        updateIndexes();
        saveData();
    }

    /**
     * Purpose - When an alarm is deleted the alarms that had an index greater than the deleted
     *           alarm have to have their absolute indexes updated to reflect their shift in position
     *           within the ListView.
     */
    private void updateIndexes() {
        for(int i=0; i<mList.size(); i++) {
            mList.get(i).setAbsIndex(i);
            Log.d("Printing List", "AbsoluteIndex: " + mList.get(i).getAbsIndex());
        }
    }

    /**
     * Purpose - Get the size of the ArrayList that holds the AlarmEntities
     * @return - integer for size of ArrayList
     */
    public int getListSize(){
        loadData();
        return mList.size();
    }

} //end of class
