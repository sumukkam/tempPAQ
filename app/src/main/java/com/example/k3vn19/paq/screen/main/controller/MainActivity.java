package com.example.k3vn19.paq.screen.main.controller;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.example.k3vn19.paq.R;
import com.example.k3vn19.paq.common.Enums;
import com.example.k3vn19.paq.screen.alarms.controller.AlarmsFragment;
import com.example.k3vn19.paq.screen.alarms.screens.detailedAlarm.controller.DetailedAlarmActivity;
import com.example.k3vn19.paq.screen.main.model.MainModel;
import com.example.k3vn19.paq.screen.main.view.MainView;
import com.example.k3vn19.paq.screen.main.view.MainViewInterface;
import com.example.k3vn19.paq.screen.more.controller.MoreFragment;
import com.example.k3vn19.paq.screen.more.screens.bluetoothFinal.BlunoLibrary;
import com.example.k3vn19.paq.screen.stopwatch.StopwatchFragment;
import com.example.k3vn19.paq.screen.timer.controller.TimerFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by k3vn19 on 1/29/2018.
 *
 * Controller for Main Page
 */

public class MainActivity extends AppCompatActivity implements MainViewInterface.MainViewListener {

    private AlarmsFragment alarmsFragment;
    private MoreFragment moreFragment;
    private StopwatchFragment stopwatchFragment;
    private TimerFragment timerFragment;

    private MainView mView;

    private Enums.TabPosition currentTabPosition;


    /*
    // TODO: keep these
    @Override
    protected void onResume() {
        System.out.println("*%^&%^*onResume");

        super.onResume();
        System.out.println("BlUNOActivity onResume");
        bluno.onResumeProcess();														//onResume Process by BlunoLibrary
    }

    @Override
    protected void onDestroy() {
        System.out.println("*%^&%^*onDestroy");

        super.onDestroy();
        bluno.onDestroyProcess();														//onDestroy Process by BlunoLibrary
    }
    */


    private TextView paqLogo;
    private BlunoLibrary bluno;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("MainActivity", "onCreate()-before tabs=================");

        super.onCreate(savedInstanceState);
        Log.d("MainActivity", "onCreate()- model=================");


        if (Build.VERSION.SDK_INT >= 23) {
            // Marshmallow+ Permission APIs
            fuckMarshMallow();
        }


        getWindow().setStatusBarColor(getResources().getColor(R.color.primaryDarkGrey));

        MainModel mModel;
        mModel = new MainModel();
        mView = new MainView(LayoutInflater.from(this), null, this);



        /*
        bluno = new BlunoLibrary(this) {
            @Override
            public void onConectionStateChange(connectionStateEnum theConnectionState) {
                System.out.println("*%^&%^*onConnectionStateChange");
            }

            @Override
            public void onSerialReceived(String theString) {

            }
        };

        bluno.onCreateProcess();
        bluno.serialBegin(115200);
        */


        paqLogo = mView.getRootView().findViewById(R.id.main_paq_logo);
        paqLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*System.out.println("paq logo pressed.");
                bluno.buttonScanOnClickProcess();
                bluno.serialSend("testing this send123");*/

                bluno = new BlunoLibrary(MainActivity.this) {
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
                bluno.buttonScanOnClickProcess();
            }
        });





        Log.d("MainActivity", "onCreate() fragment[] =================");

        Fragment[] fragments = new Fragment[4];
        alarmsFragment = new AlarmsFragment();
        moreFragment = new MoreFragment();
        stopwatchFragment = new StopwatchFragment();
        timerFragment = new TimerFragment();

        Log.d("MainActivity", "onCreate()-after fragment[]=================");

        fragments[Enums.TabPosition.ALARMS.getVal()] = alarmsFragment;
        fragments[Enums.TabPosition.MORE.getVal()] = moreFragment;
        fragments[Enums.TabPosition.STOPWATCH.getVal()] = stopwatchFragment;
        fragments[Enums.TabPosition.TIMER.getVal()] = timerFragment;


        Log.d("MainActivity", "onCreate()-setup tabs=================");

        mView.setUpTabs(fragments, mModel.tabTitles,0,getSupportFragmentManager());
        currentTabPosition = Enums.TabPosition.MORE;
        setContentView(mView.getRootView());

        Log.d("MainActivity", "onCreate()-after tabs=================");

        invalidateOptionsMenu();
        setSupportActionBar(mView.getToolbar());
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        mView.setToolbarTitle("Alarms");

        Log.d("MainActivity", "onCreate() - complete=================");

    }

    /* FOR WHEN WE HAVE USER ACCOUNTS
    private void goToLogin() {
        startActivity(new Intent(this, LoginActivity.class));
    }
    private void goToMain() {
        startActivity(new Intent(this, MainActivity.class));
    }
    private void goToSignup() {
        startActivity(new Intent(this, SignupActivity.class));
    }
    */

    @Override
    public void tabChanged(Enums.TabPosition position) {

        currentTabPosition = position;


        if(position == Enums.TabPosition.ALARMS) {
            alarmsFragment.isScreenShow(true);
            moreFragment.isScreenShow(false);
            stopwatchFragment.isScreenShow(false);
            timerFragment.isScreenShow(false);

            mView.setToolbarTitle("Alarms");

        } else if(position == Enums.TabPosition.MORE) {
            alarmsFragment.isScreenShow(true);
            moreFragment.isScreenShow(false);
            stopwatchFragment.isScreenShow(false);
            timerFragment.isScreenShow(false);

            mView.setToolbarTitle("More");


        } else if(position == Enums.TabPosition.STOPWATCH) {
            alarmsFragment.isScreenShow(true);
            moreFragment.isScreenShow(false);
            stopwatchFragment.isScreenShow(false);
            timerFragment.isScreenShow(false);

            mView.setToolbarTitle("Stopwatch");

        } else if(position == Enums.TabPosition.TIMER) {
            alarmsFragment.isScreenShow(true);
            moreFragment.isScreenShow(false);
            stopwatchFragment.isScreenShow(false);
            timerFragment.isScreenShow(false);

            mView.setToolbarTitle("Timer");
        }

    }

    @Override
    public void createNewAlarm(){
        Intent detailedAlarmActivity = new Intent(MainActivity.this, DetailedAlarmActivity.class);
        detailedAlarmActivity.putExtra("index", -1);
        startActivity(detailedAlarmActivity);

        //TODO - If you want to change animation do it here
    }

    @Override
    public void onBackPressed() {
        Fragment fragment;

        if(currentTabPosition == Enums.TabPosition.ALARMS) {
            fragment = alarmsFragment;
        } else if(currentTabPosition == Enums.TabPosition.MORE){
            fragment = moreFragment;
        } else if(currentTabPosition == Enums.TabPosition.STOPWATCH){
            fragment = stopwatchFragment;
        } else{
            fragment = timerFragment;
        }

        if(fragment.getChildFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
        } else {
            fragment.getChildFragmentManager().popBackStack();
        }
    }







    /**
     * ASK FOR PERMISSIONS
     */
    @TargetApi(Build.VERSION_CODES.M)
    private void fuckMarshMallow() {
        List<String> permissionsNeeded = new ArrayList<String>();

        final List<String> permissionsList = new ArrayList<String>();
        if (!addPermission(permissionsList, Manifest.permission.ACCESS_FINE_LOCATION))
            permissionsNeeded.add("Show Location");

        if (permissionsList.size() > 0) {
            if (permissionsNeeded.size() > 0) {

                // Need Rationale
                String message = "App need access to " + permissionsNeeded.get(0);

                for (int i = 1; i < permissionsNeeded.size(); i++)
                    message = message + ", " + permissionsNeeded.get(i);

                showMessageOKCancel(message,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]), 124);
                            }
                        });
                return;
            }
            requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                    124);
            return;
        }

        Toast.makeText(MainActivity.this, "No new Permission Required- Launching App .You are Awesome!!", Toast.LENGTH_SHORT)
                .show();
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @TargetApi(Build.VERSION_CODES.M)
    private boolean addPermission(List<String> permissionsList, String permission) {

        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
            // Check for Rationale Option
            if (!shouldShowRequestPermissionRationale(permission))
                return false;
        }
        return true;
    }



}//end of class
