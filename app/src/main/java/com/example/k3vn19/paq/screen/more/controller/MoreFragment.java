package com.example.k3vn19.paq.screen.more.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.example.k3vn19.paq.screen.more.screens.aboutPAQ.AboutPAQActivity;
import com.example.k3vn19.paq.screen.more.screens.bluetoothFinal.BlunoLibrary;
import com.example.k3vn19.paq.screen.more.screens.bluetoothFinal.MainActivityBluetooth;
import com.example.k3vn19.paq.screen.more.screens.faq.FAQActivity;
import com.example.k3vn19.paq.screen.more.view.MoreView;
import com.example.k3vn19.paq.screen.more.view.MoreViewInterface;

/**
 * Created by k3vn19 on 2/5/2018.
 *
 * Purpose - This package is for the MORE Tab. This tab will have a list of options to choose from:
 *           About PAQ, Connect Device, FAQ, legal terms etc.
 */

public class MoreFragment extends Fragment implements MoreViewInterface.MoreViewListener{

    MoreView mView;
    private Boolean isScreenShow;

    BlunoLibrary bluno;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceBundle){

        mView = new MoreView(inflater, container, this);

        return mView.getRootView();
    }

    /**
     * This method handles whether this screen is going to be displayed or not
     *
     */
    public void isScreenShow(boolean isScreenShow) {
        this.isScreenShow = isScreenShow;
    }

    /**
     * Purpose - move from more to AboutPaq page.
     */
    public void goToAbout(){
        Intent intent = new Intent(getActivity(), AboutPAQActivity.class);
        startActivity(intent);
    }

    /**
     * Purpose - move from more to ConnectDevice page.
     */
    public void goToConnectDevice(){
        //Intent intent = new Intent(getActivity(), TempBluetoothMainActivity.class);
        //Intent intent = new Intent(getActivity(), BluetoothActivity.class);
        Intent intent = new Intent(getActivity(), MainActivityBluetooth.class);
        startActivity(intent);

        /*bluno = new BlunoLibrary(getActivity()) {
            @Override
            public void onConectionStateChange(connectionStateEnum theConnectionState) {
                System.out.println("*%^&%^*onConnectionStateChange");
            }

            @Override
            public void onSerialReceived(String theString) {
                System.out.println("*%^&%^*onSerialReceived");
            }
        };
        bluno.onCreateProcess();
        bluno.serialBegin(115200);
        bluno.buttonScanOnClickProcess();*/
    }

    /**
     * Purpose - move from more to FAQ page.
     */
    public void goToFAQ(){
        Intent intent = new Intent(getActivity(), FAQActivity.class);
        startActivity(intent);
    }

    /**
     * Purpose - move from more to Report a Problem page.
     */
    public void goToReport(){

    }

    public Boolean getScreenShow() {
        return isScreenShow;
    }
}
