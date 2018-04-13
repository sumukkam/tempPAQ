package com.example.k3vn19.paq.screen.more.screens.aboutPAQ;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;

/**
 * Created by k3vn19 on 2/5/2018.
 *
 * Activity for AboutPAQ screen.
 */

public class AboutPAQActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AboutPAQView mView = new AboutPAQView(LayoutInflater.from(this), null);

        setContentView(mView.getRootView());
    }


} //end of class
