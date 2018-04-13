package com.example.k3vn19.paq.screen.start.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
//import android.view.View;

import com.example.k3vn19.paq.R;
import com.example.k3vn19.paq.screen.main.controller.MainActivity;
import com.example.k3vn19.paq.screen.start.model.StartModel;
import com.example.k3vn19.paq.screen.start.view.StartView;

/**
 * Created by k3vn19 on 1/29/2018.
 *
 * Purpose - Controller for StartActivity.
 */

public class StartActivity extends AppCompatActivity {

    private StartModel mModel;  //Currenly not used, will be used when we have accounts

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Start===============","onCreate()===========");

        getWindow().setStatusBarColor(getResources().getColor(R.color.primaryDarkGrey));

        //create view and attach to activity
        StartView mView = new StartView(LayoutInflater.from(this), null);
        setContentView(mView.getRootView());

        //create model - will be used for checking if user is logged in
        mModel = new StartModel();

        //this was made by default when creating android projct
        //setContentView(R.layout.view_start);
    }



    @Override
    protected void onStart(){
        super.onStart();

        if(mModel.isLoggedIn()){
            //Toast.makeText(this, FirebaseAuthManager.getEmail(), Toast.LENGTH_SHORT).show();
            //Toast.makeText(this, FirebaseAuthManager.getUserId(), Toast.LENGTH_SHORT).show();

            //if user is already logged in, then go to Main game page.
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            //otherwise, if not logged in, redirect to log in page.
            Intent intent = new Intent(this, MainActivity.class); //change this to LoginActivity
            startActivity(intent);
            finish();
        }
    }

}
