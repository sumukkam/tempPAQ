package com.example.k3vn19.paq.screen.alarms.screens.alarmsMain.controller;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.k3vn19.paq.screen.alarms.screens.alarmsMain.AlarmsMainListener;
import com.example.k3vn19.paq.screen.alarms.screens.alarmsMain.model.AlarmsMainModel;
import com.example.k3vn19.paq.screen.alarms.screens.alarmsMain.view.AlarmsMainView;
import com.example.k3vn19.paq.screen.alarms.screens.alarmsMain.view.AlarmsMainViewInterface;
import com.example.k3vn19.paq.utils.database.DataBaseHelper;

/**
 * Created by k3vn19 on 1/30/2018.
 *
 * Purpose - Controller for AlarmsMain. Provides logic for switching between list of alarms and
 *           individual alarm pages.
 */

public class AlarmsMainFragment extends Fragment implements AlarmsMainViewInterface.AlarmsMainViewListener {

    private AlarmsMainView mView;
    private AlarmsMainModel mModel;
    private AlarmsMainListener mListener;

    private DataBaseHelper helper;

    //Flag is true when AlarmsMainFragment is shown
    private boolean isScreenShow = false;

    public void setupFragment(AlarmsMainListener listner){
        this.mListener = listner;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        mView = new AlarmsMainView(inflater, container, this, getContext());

        return mView.getRootView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(this.getActivity() != null) {
            helper = new DataBaseHelper(this.getActivity());
            mModel = new AlarmsMainModel(helper.getList());
            this.setAlarmsToListView();

        }
        else{
            throw new NullPointerException("Null pointer in AlarmsMainFragment");
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d("AlarmsMainFrag:onResume", "resuming the fragment containing the list of alarms.");

        mModel = new AlarmsMainModel(helper.getList());

        Log.d("AlarmsMainFrag:onResume", "created model===============================");
        this.setAlarmsToListView();
    }

    /**
     * Purpose - Get list of alarms from model.
     */
    private void setAlarmsToListView(){
        mView.setUpListView(mModel.getAlarmsEntityList());
    }

    @Override
    public void listTap(int i){
        mListener.moveToDetailFragment(i);
    }

    @Override
    public void deleteItem(int i){
        mModel.deleteAlarm(i, helper);

        //notify the ListView that an item has been deleted
        mView.notifyChange();
    }

    public void setIsScreenShow(boolean isScreenShow){
        this.isScreenShow = isScreenShow;
    }

} //end of class
